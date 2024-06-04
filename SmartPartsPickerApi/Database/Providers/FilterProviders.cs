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
                ProductType = entity.ProductType,
                ParamName = entity.ParamName,
            },
            x => new FilterTable()
            {
                Id = entity.Id,
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

        public FilterTable GetByProductType(ProductType productType)
        {
            using var db = new DbWinbroker();
            return db.Filters.FirstOrDefault(x => x.ProductType == productType);
        }
    }
}
