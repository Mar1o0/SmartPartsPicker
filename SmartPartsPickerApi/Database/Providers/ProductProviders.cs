using LinqToDB;
using SmartPartsPickerApi.Database.Tables;
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

        public ProductTable GetById(int id)
        {
            using var db = new DbWinbroker();
            return db.Products.FirstOrDefault(x => x.Id == id);
        }

        public List<ProductTable> GetAllByType(ProductType type)
        {
            using var db = new DbWinbroker();
            return db.Products.Where(x => x.Type == type).ToList();
        }
        public ProductTable GetProductById(int id)
        {
            using var db = new DbWinbroker();
            var product = db.Products.FirstOrDefault(x => x.Id == id);

            var productResponses = new ProductTable
            {
                Id = product.Id,
                Type = product.Type,
                ApiId = product.ApiId,
                Name = product.Name,
                FullName = product.FullName,
                Description = product.Description,
                ProductImage = db.ProductImages.FirstOrDefault(i => i.ProductId == product.Id),
                Price = db.ProductPrices.Where(pr => pr.ProductId == product.Id).ToList(),
                Specs = db.ProductSpecs.Where(i => i.ProductId == product.Id).ToList(),
            };

            return productResponses;
        }
        
        public List<ProductTable> GetAllProductByFilters(ProductType productType,
                                                         double priceMin = 0,
                                                         double priceMax = double.MaxValue,
                                                         string filters = null,
                                                         int page = 1,
                                                         int per_page = 20)
        {
            using var db = new DbWinbroker();

            var productsQuery = db.Products.Where(x => x.Type == productType)
                                           .Where(p =>
                                           db.ProductPrices.Any(pr => pr.ProductId == p.Id && pr.Price >= priceMin && pr.Price <= priceMax));

            if (!string.IsNullOrEmpty(filters))
            {
                var filterIds = filters.Split(',').Select(int.Parse).ToList();
                productsQuery = productsQuery.Where(p => db.ProductFilters.Any(pf => pf.ProductId == p.Id && filterIds.Contains(pf.FilterId)));
            }

            productsQuery = productsQuery.Skip((page - 1) * per_page).Take(per_page);

            var products = productsQuery.ToList();
            var productIds = products.Select(p => p.Id).ToList();
            var allImages = db.ProductImages.Where(i => productIds.Contains(i.ProductId)).ToList();
            var allPrices = db.ProductPrices.Where(pr => productIds.Contains(pr.ProductId)).ToList();
            var allSpecs = db.ProductSpecs.Where(s => productIds.Contains(s.ProductId)).ToList();

            var productResponses = products.Select(p => new ProductTable
            {
                Id = p.Id,
                Type = p.Type,
                ApiId = p.ApiId,
                Name = p.Name,
                FullName = p.FullName,
                Description = p.Description,
                ProductImage = allImages.FirstOrDefault(i => i.ProductId == p.Id),
                Price = allPrices.Where(pr => pr.ProductId == p.Id).ToList(),
                Specs = allSpecs.Where(s => s.ProductId == p.Id).ToList(),
            }).ToList();

            return productResponses;
        }
    }
}
