using LinqToDB;
using SmartPartsPickerApi.Database.Tables;

namespace SmartPartsPickerApi.Database.Providers
{
    public class ProductImageProviders
    {
        public async Task<ProductImageTable> CreateUpdate(ProductImageTable entity)
        {
            using var db = new DbWinbroker();
            var id = db.ProductImages.Any() ? db.ProductImages.Max(x => x.Id) + 1 : 1;
            entity.Id = id;
            await db.ProductImages.InsertOrUpdateAsync(() => new ProductImageTable()
            {
                Id = entity.Id,
                Href = entity.Href,
                ProductId = entity.ProductId
            },
            x => new ProductImageTable()
            {
                Id = entity.Id,
                Href = entity.Href ?? x.Href,
                ProductId = entity.ProductId == x.ProductId ? entity.ProductId : x.ProductId,
            });
            return entity;
        }

        public List<ProductImageTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.ProductImages.ToList();
        }

        public ProductImageTable GetById(int id)
        {
            using var db = new DbWinbroker();
            return db.ProductImages.FirstOrDefault(x => x.Id == id);
        }

        public ProductImageTable GetByProductId(int productId)
        {
            using var db = new DbWinbroker();
            return db.ProductImages.FirstOrDefault(x => x.ProductId == productId);
        }
    }
}
