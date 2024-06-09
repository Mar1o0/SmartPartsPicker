using LinqToDB;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Database.Providers
{
    public class ProductFiltersProviders
    {
        public async Task<ProductFiltersTable> CreateUpdate(ProductFiltersTable entity)
        {
            using var db = new DbWinbroker();

            await db.ProductFilters.InsertOrUpdateAsync(() => new ProductFiltersTable()
            {
                FilterId = entity.FilterId,
                ProductId = entity.ProductId
            },
            x => new ProductFiltersTable()
            {
                FilterId = entity.FilterId,
                ProductId = entity.ProductId
            });
            return entity;
        }

        public List<ProductFiltersTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.ProductFilters.ToList();
        }

        public List<ProductFiltersTable> GetByProductId(int productId)
        {
            using var db = new DbWinbroker();
            return db.ProductFilters.Where(x => x.ProductId == productId).ToList();
        }
    }
}
