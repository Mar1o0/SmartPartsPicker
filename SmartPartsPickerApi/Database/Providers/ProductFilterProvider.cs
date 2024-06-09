using LinqToDB;
using SmartPartsPickerApi.Database.Tables;

namespace SmartPartsPickerApi.Database.Providers
{
    public class ProductFilterProvider
    {
        public async Task<ProductFilterTable> CreateUpdate(ProductFilterTable entity)
        {
            using var db = new DbWinbroker();
            await db.ProductFilters.InsertAsync(() => new ProductFilterTable()
            {
                FilterId = entity.FilterId,
                ProductId = entity.ProductId,
            });
            return entity;
        }

        public List<ProductFilterTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.ProductFilters.ToList();
        }

        public List<ProductTable> GetProducts(int[] filterIds)
        {
            using var db = new DbWinbroker();
            var productIds = db.ProductFilters.Where(x=> filterIds.Contains(x.FilterId)).Select(x => x.ProductId).ToList();
            return db.Products.Where(x => productIds.Contains(x.Id)).ToList();
        }
        public List<FilterTable> GetFiters(int[] productIds) // useless mbmbmbmmb
        {
            using var db = new DbWinbroker();
            var filterIds = db.ProductFilters.Where(x=> productIds.Contains(x.ProductId)).Select(x => x.FilterId).ToList();
            return db.Filters.Where(x => filterIds.Contains(x.Id)).ToList();
        }
        //TODO: Add delete
    }
}
