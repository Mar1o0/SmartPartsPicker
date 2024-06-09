using LinqToDB;
using SmartPartsPickerApi.Database.Tables;

namespace SmartPartsPickerApi.Database.Providers
{
    public class ProductPriceProviders
    {
        public async Task<ProductPriceTable> CreateUpdate(ProductPriceTable entity)
        {
            using var db = new DbWinbroker();
            var id = db.ProductPrices.Any() ? db.ProductPrices.Max(x => x.Id) + 1 : 1;
            entity.Id = id;
            await db.ProductPrices.InsertOrUpdateAsync(() => new ProductPriceTable()
            {
                Id = entity.Id,
                Currency = entity.Currency,
                Price = entity.Price,
                Href = entity.Href,
                ProductId = entity.ProductId,
                ShopName = entity.ShopName,
                ShopImage = entity.ShopImage,
            },
            x => new ProductPriceTable()
            {
                Id = entity.Id,
                Currency = entity.Currency ?? x.Currency,
                Price = entity.Price == x.Price ? entity.Price : x.Price,
                Href = entity.Href ?? x.Href,
                ProductId = entity.ProductId == x.ProductId ? entity.ProductId : x.ProductId,
                ShopName = entity.ShopName ?? x.ShopName,
                ShopImage = entity.ShopImage ?? x.ShopImage,
            });
            return entity;
        }

        public List<ProductPriceTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.ProductPrices.ToList();
        }

        public ProductPriceTable GetById(int id)
        {
            using var db = new DbWinbroker();
            return db.ProductPrices.FirstOrDefault(x => x.Id == id);
        }

        public List<ProductPriceTable> GetByPrice(double price)
        {
            using var db = new DbWinbroker();
            return db.ProductPrices.Where(x => x.Price == price).ToList();
        }
        public List<ProductPriceTable> GetByPrice(double toPrice, double fromPrice)
        {
            using var db = new DbWinbroker();
            return db.ProductPrices.Where(x => x.Price >= toPrice && x.Price <= fromPrice).ToList();
        }

        internal List<ProductPriceTable> GetByProductId(int productId)
        {
            using var db = new DbWinbroker();
            return db.ProductPrices.Where(x => x.ProductId == productId).ToList();
        }
    }
}
