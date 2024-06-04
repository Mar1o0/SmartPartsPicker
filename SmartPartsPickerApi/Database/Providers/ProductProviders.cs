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
            var id = db.Products.Any() ? db.Products.Max(x => x.Id) + 1 : 1;
            entity.Id = id;
            await db.Products.InsertOrUpdateAsync(() => new ProductTable()
            {
                Id = entity.Id,
                Name = entity.Name,
                FullName = entity.FullName,
                Description = entity.Description,
                ApiId = entity.ApiId,
                ProductImages = entity.ProductImages,
                ProductPrices = entity.ProductPrices,
                ProductSpecs = entity.ProductSpecs,
                Type = entity.Type,
            },
            x => new ProductTable()
            {
                Id = entity.Id,
                Name = entity.Name ?? x.Name,
                FullName = entity.FullName ?? x.FullName,
                Description = entity.Description ?? x.Description,
                ApiId = entity.ApiId ?? x.ApiId ,
                ProductImages = entity.ProductImages ?? x.ProductImages,
                ProductPrices = entity.ProductPrices ?? x.ProductPrices,
                ProductSpecs = entity.ProductSpecs ?? x.ProductSpecs,
                Type = x.Type,
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
    }
}
