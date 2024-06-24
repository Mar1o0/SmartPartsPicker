using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

namespace SmartPartsPickerApi.Service.Filters
{
    public class StorageFilterParser : IProductFilterParser
    {
        private const string HDD_NAME_PREFIX = "Жесткий диск";
        private const string SSD_NAME_PREFIX = "SSD";
        public ProductType ProductType => ProductType.STORAGE;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x => x.name_prefix == HDD_NAME_PREFIX || x.name_prefix == SSD_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();

            var mfrVariants = new HashSet<string>();
            var interfaceVariants = new HashSet<string>();
            var speedVariants = new HashSet<string>();
            var formFactorVariants = new HashSet<string>();
            var sizeVariants = new HashSet<string>();

            foreach (var product in productsToParse)
            {
                var mfr = product.full_name.Split(' ').First().Trim();
                var size = product.description_list.FirstOrDefault(x => x.Contains(" ТБ", StringComparison.InvariantCultureIgnoreCase) || x.Contains(" ГБ", StringComparison.InvariantCultureIgnoreCase));
                var interfaceType = product.description_list.FirstOrDefault(x => x.Contains("SATA", StringComparison.InvariantCultureIgnoreCase) || x.Contains("PCI Express", StringComparison.InvariantCultureIgnoreCase));
                var formFactor = product.description_list.FirstOrDefault(x => x.Contains("M.2", StringComparison.InvariantCultureIgnoreCase) || x.Contains("2.5\\\"", StringComparison.InvariantCultureIgnoreCase) || x.Contains("3.5\\\"", StringComparison.InvariantCultureIgnoreCase));
                var speed = product.description_list.FirstOrDefault(x => x.Contains("об/мин", StringComparison.InvariantCultureIgnoreCase));

                AddToHashSet(mfrVariants, mfr);
                AddToHashSet(sizeVariants, size);
                AddToHashSet(interfaceVariants, interfaceType);
                AddToHashSet(formFactorVariants, formFactor);
                AddToHashSet(speedVariants, speed);
            }

            AddFilters(availableFilters, mfrVariants, StorageFilterType.Manufacturer);
            AddFilters(availableFilters, sizeVariants, StorageFilterType.Size);
            AddFilters(availableFilters, interfaceVariants, StorageFilterType.Sata);
            AddFilters(availableFilters, formFactorVariants, StorageFilterType.FormFactor);
            AddFilters(availableFilters, speedVariants, StorageFilterType.Speed);

            return availableFilters;
        }

        private void AddToHashSet(HashSet<string> hashSet, string value)
        {
            if (value != null && !hashSet.Contains(value))
            {
                hashSet.Add(value);
            }
        }

        private void AddFilters(List<IProductFilter> filters, HashSet<string> values, StorageFilterType filterType)
        {
            foreach (var value in values)
            {
                filters.Add(new StorageFilter(filterType, value));
            }
        }
    }
}
