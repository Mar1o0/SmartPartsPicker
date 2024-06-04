using LinqToDB;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Database.Providers
{
    public class ProductSpecProviders
    {
        public async Task<ProductSpecTable> CreateUpdate(ProductSpecTable entity)
        {
            using var db = new DbWinbroker();
            var id = db.ProductSpecs.Any() ? db.ProductSpecs.Max(x => x.Id) + 1 : 1;
            entity.Id = id;
            await db.ProductSpecs.InsertOrUpdateAsync(() => new ProductSpecTable()
            {
                Id = entity.Id,
                ProductId = entity.ProductId,
                Type = entity.Type,
                Value = entity.Value
            },
            x => new ProductSpecTable()
            {
                Id = entity.Id,
                ProductId = entity.ProductId == x.ProductId ? x.ProductId : entity.ProductId,
                Type = entity.Type == x.Type ? x.Type : entity.Type,
                Value = entity.Value ?? x.Value,
            });
            return entity;
        }

        public List<ProductSpecTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.ProductSpecs.ToList();
        }

        public ProductSpecTable GetByProductId(int productId)
        {
            using var db = new DbWinbroker();
            return db.ProductSpecs.FirstOrDefault(x => x.ProductId == productId);
        }

        public List<ProductSpecTable> GetAllBySpecType(SpecType type)
        {
            using var db = new DbWinbroker();
            return db.ProductSpecs.Where(x => x.Type == type).ToList();
        }
    }
}
