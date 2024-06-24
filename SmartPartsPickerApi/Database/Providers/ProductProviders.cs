using LinqToDB;
using LinqToDB.Data;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Database.Views;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

namespace SmartPartsPickerApi.Database.Providers
{
    public class ProductProviders
    {
        public async Task<ProductTable> CreateUpdate(ProductTable entity)
        {
            using var db = new DbWinbroker();
            if (entity.Id == 0)
            {
                var id = db.Products.Any() ? db.Products.Max(x => x.Id) + 1 : 1;
                entity.Id = id;
            }

            await db.Products.InsertOrUpdateAsync(() => new ProductTable()
            {
                Id = entity.Id,
                Name = entity.Name,
                FullName = entity.FullName,
                Description = entity.Description,
                ApiId = entity.ApiId,
                Type = entity.Type,
                Rating = entity.Rating,
            },
            x => new ProductTable()
            {
                Id = entity.Id,
                Name = entity.Name ?? x.Name,
                FullName = entity.FullName ?? x.FullName,
                Description = entity.Description ?? x.Description,
                ApiId = entity.ApiId,
                Type = x.Type,
                Rating = entity.Rating
            });
            return entity;
        }

        public List<ProductTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.Products.ToList();
        }

        public FilteredProductView GetProductById(int id)
        {
            using var db = new DbWinbroker();
            var product = db.Products.FirstOrDefault(x => x.Id == id);

            var productResponses = db.Query<FilteredProductView>(
                "Select p.*, pi.href From Product p " +
                "INNER JOIN ProductImage pi on pi.productId = p.id " +
                $"Where p.id = {id};"
                ).FirstOrDefault();

            var filters = db.Query<FilterTable>(
                "Select f.* from Filter f " +
                "INNER JOIN ProductFilter pf on pf.filter_id = f.id " +
                $"Where pf.product_id = {id};").ToList();

            var prices = db.Query<ProductPriceTable>(
                "SELECT pp.* FROM ProductPrice pp " +
                $"WHERE pp.productId = {id};" +
                "").ToList();

            switch (productResponses.Type)
            {
                case ProductType.CPU:
                    productResponses.Specs = filters.Select(x => new CpuFilter(x)).Select(x => (IProductFilter)x).ToList();
                    break;
                case ProductType.GPU:
                    productResponses.Specs = filters.Select(x => new VideoCardFilter(x)).Select(x => (IProductFilter)x).ToList();
                    break;
                case ProductType.PSU:
                    productResponses.Specs = filters.Select(x => new PowerSupplyFilter(x)).Select(x => (IProductFilter)x).ToList();
                    break;
                case ProductType.RAM:
                    productResponses.Specs = filters.Select(x => new DramFilter(x)).Select(x => (IProductFilter)x).ToList();
                    break;
                case ProductType.CHASSIS:
                    productResponses.Specs = filters.Select(x => new ChassisFilter(x)).Select(x => (IProductFilter)x).ToList();
                    break;
                case ProductType.MB:
                    productResponses.Specs = filters.Select(x => new MotherBoardFilter(x)).Select(x => (IProductFilter)x).ToList();
                    break;
                case ProductType.STORAGE:
                    productResponses.Specs = filters.Select(x => new StorageFilter(x)).Select(x => (IProductFilter)x).ToList();
                    break;
            }

            productResponses.Price = prices;

            return productResponses;
        }

        public List<FilteredProductView> GetProductBySearch(string search, ProductType productType)
        {
            using var db = new DbWinbroker();

            var productResponses = db.Query<FilteredProductView>(
                "Select DISTINCT p.*, pi.href From Product p " +
                "INNER JOIN ProductImage pi on pi.productId = p.id " +
                $"Where p.fullname LIKE '%{search}%' AND type = {(int)productType};"
                ).ToList();

            foreach (var product in productResponses)
            {
                product.Price = db.Query<ProductPriceTable>(
                "SELECT pp.* FROM ProductPrice pp " +
                $"WHERE pp.productId = {product.Id};" +
                "").ToList();

                var filtersSpec = db.Query<FilterTable>(
                                "Select f.* from Filter f " +
                                "INNER JOIN ProductFilter pf on pf.filter_id = f.id " +
                                $"Where pf.product_id = {product.Id};").ToList();

                switch (product.Type) // не надо так делать больше  (((
                {
                    case ProductType.CPU:
                        product.Specs = filtersSpec.Select(x => new CpuFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.GPU:
                        product.Specs = filtersSpec.Select(x => new VideoCardFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.PSU:
                        product.Specs = filtersSpec.Select(x => new PowerSupplyFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.RAM:
                        product.Specs = filtersSpec.Select(x => new DramFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.CHASSIS:
                        product.Specs = filtersSpec.Select(x => new ChassisFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.MB:
                        product.Specs = filtersSpec.Select(x => new MotherBoardFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.STORAGE:
                        product.Specs = filtersSpec.Select(x => new StorageFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                }
            }

            return productResponses;
        }

        public List<FilteredProductView> GetAllProductByFilters(ProductType productType,
                                                         double priceMin = 0,
                                                         double priceMax = double.MaxValue,
                                                         string filters = null,
                                                         int page = 1,
                                                         int per_page = 20)
        {
            using var db = new DbWinbroker();
            if (--page < 0)
            {
                throw new Exception("Page must be over 1");
            }

            var filterCondition = string.IsNullOrEmpty(filters) ? "" : $"AND pf.filter_id in ({filters})";

            var q = $@"
                    SELECT DISTINCT p.*, pi.href, price_data.min_price as Price
                    FROM Product p
                    INNER JOIN ProductFilter pf ON pf.product_id = p.id
                    INNER JOIN ProductImage pi ON pi.productId = p.id
                    INNER JOIN (
                        SELECT pp.productId, MIN(pp.price) as min_price
                        FROM ProductPrice pp
                        GROUP BY pp.productId
                    ) price_data ON price_data.productId = p.id
                    WHERE p.type = {(int)productType}
                    AND price_data.min_price BETWEEN {priceMin.ToString().Replace(',', '.')} AND {priceMax.ToString().Replace(',', '.')}
                    {filterCondition}
                    LIMIT {per_page} OFFSET {page * per_page};
                    ";

            // Выполнение запроса и получение результата

            var productResponses = db.Query<FilteredProductView>(q).ToList();

            if (productResponses.Count == 0)
            {
                return productResponses;
            }

            var productIds = string.Join(", ", productResponses.Select(p => p.Id));

            var priceQuery = $@"
                                SELECT pp.*
                                FROM ProductPrice pp
                                WHERE pp.productId IN ({productIds});
                             ";

            var prices = db.Query<ProductPriceTable>(priceQuery).ToList();

            foreach (var product in productResponses)
            {
                product.Price = prices.Where(x => x.ProductId == product.Id).ToList();

                var filtersSpec = db.Query<FilterTable>(
                                "Select f.* from Filter f " +
                                "INNER JOIN ProductFilter pf on pf.filter_id = f.id " +
                                $"Where pf.product_id = {product.Id};").ToList();

                switch (product.Type) // не надо так делать больше  (((
                {
                    case ProductType.CPU:
                        product.Specs = filtersSpec.Select(x => new CpuFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.GPU:
                        product.Specs = filtersSpec.Select(x => new VideoCardFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.PSU:
                        product.Specs = filtersSpec.Select(x => new PowerSupplyFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.RAM:
                        product.Specs = filtersSpec.Select(x => new DramFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.CHASSIS:
                        product.Specs = filtersSpec.Select(x => new ChassisFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.MB:
                        product.Specs = filtersSpec.Select(x => new MotherBoardFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                    case ProductType.STORAGE:
                        product.Specs = filtersSpec.Select(x => new StorageFilter(x)).Select(x => (IProductFilter)x).ToList();
                        break;
                }
            }

            return productResponses;
        }
    }
}
