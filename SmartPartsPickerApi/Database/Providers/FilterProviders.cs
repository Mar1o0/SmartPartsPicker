using LinqToDB;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Database.Providers
{
    public class FilterProviders
    {
        public async Task<FilterTable> CreateUpdate(FilterTable entity)
        {
            using var db = new DbWinbroker();
            var id = db.Filters.Any() ? db.Filters.Max(x => x.Id) + 1 : 1;
            entity.Id = id;
            await db.Filters.InsertOrUpdateAsync(() => new FilterTable()
            {
                Id = entity.Id,
                FilterType = entity.FilterType,
                FilterVariat = entity.FilterVariat,
                ProductType = entity.ProductType,
                ParamName = entity.ParamName,
            },
            x => new FilterTable()
            {
                Id = entity.Id,
                FilterType = entity.FilterType,
                FilterVariat = entity.FilterVariat,
                ProductType = entity.ProductType == x.ProductType ? x.ProductType : entity.ProductType,
                ParamName = entity.ParamName ?? x.ParamName,
            });
            return entity;
        }

        public List<FilterTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.Filters.ToList();
        }

        public List<FilterTable> GetByProductType(ProductType productType)
        {
            using var db = new DbWinbroker();
            return db.Filters.Where(x => x.ProductType == productType).ToList();
        }
        public int Delete(ProductType productType)
        {
            using var db = new DbWinbroker();
            return db.Filters.Delete(x=>x.ProductType == productType);
        }
    }
}
