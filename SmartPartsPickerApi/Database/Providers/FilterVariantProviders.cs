using LinqToDB;
using SmartPartsPickerApi.Database.Tables;

namespace SmartPartsPickerApi.Database.Providers
{
    public class FilterVariantProviders
    {
        public async Task<FilterVariantTable> CreateUpdate(FilterVariantTable entity)
        {
            using var db = new DbWinbroker();
            var id = db.FilterVariants.Any() ? db.FilterVariants.Max(x => x.Id) + 1 : 1;
            entity.Id = id;
            await db.FilterVariants.InsertOrUpdateAsync(() => new FilterVariantTable()
            {
                Id = entity.Id,
                FilterId = entity.FilterId,
                Variant = entity.Variant,
                FriendlyVariantName = entity.FriendlyVariantName,
            },
            x => new FilterVariantTable()
            {
                Id = entity.Id,
                FilterId = entity.FilterId == x.FilterId ? x.FilterId : entity.FilterId,
                Variant = entity.Variant ?? x.Variant,
                FriendlyVariantName = entity.FriendlyVariantName ?? x.FriendlyVariantName,
            });
            return entity;
        }

        public List<FilterVariantTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.FilterVariants.ToList();
        }

        public FilterVariantTable GetByFilterId(int filterId)
        {
            using var db = new DbWinbroker();
            return db.FilterVariants.FirstOrDefault(x => x.FilterId == filterId);
        }
    }
}
