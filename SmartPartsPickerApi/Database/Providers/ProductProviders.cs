using LinqToDB;
using LinqToDB.Data;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Database.Views;
using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Database.Providers
{
    public class ProductProviders
    {
        public async Task<ProductTable> CreateUpdate(ProductTable entity)
        {
            using var db = new DbWinbroker();
            if(entity.Id == 0)
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
                Reviews = entity.Reviews,
            },
            x => new ProductTable()
            {
                Id = entity.Id,
                Name = entity.Name ?? x.Name,
                FullName = entity.FullName ?? x.FullName,
                Description = entity.Description ?? x.Description,
                ApiId = entity.ApiId,
                Type = x.Type,
                Reviews = entity.Reviews 
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

            var prices = db.Query<ProductPriceTable>(
                "SELECT pp.* FROM ProductPrice pp " +
                $"WHERE pp.productId = {id};" +
                "").ToList();



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
            if(--page < 0)
            {
                throw new Exception("Page must be over 1");
            }

            var q = "Select DISTINCT p.*, pi.href From Product p " +
                "INNER JOIN ProductFilter pf on pf.product_id = p.id " +
                "INNER JOIN ProductImage pi on pi.productId = p.id " +
                $"Where {(string.IsNullOrEmpty(filters) ? "" : $"pf.filter_id in ({filters}) AND")} p.type = {(int)productType} " +
                $"LIMIT {per_page} OFFSET {page * per_page};";

            var productResponses = db.Query<FilteredProductView>(
                q
                ).ToList();

            var productIds = productResponses.Select(x => x.Id);

            var prices = db.Query<ProductPriceTable>(
                "SELECT pp.* FROM ProductPrice pp " +
                $"WHERE pp.productId in ({string.Join(", ", productIds)});" +
                "").ToList(); // 8ms

            //var q = db.ProductPrices.Where(x => productIds.Contains(x.ProductId));

            //Console.WriteLine(q.ToString());

            //var prices = q.ToList(); // 161ms

            foreach (var product in productResponses)
            {
                product.Price = prices.Where(x => x.ProductId == product.Id).ToList();
            }

            productResponses = productResponses
                .Where(x => priceMin < x.Price.Min(p => p.Price) && priceMax > x.Price.Min(p => p.Price))
                .ToList();

            return productResponses;
        }
    }
}
