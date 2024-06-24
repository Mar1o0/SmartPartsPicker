using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Models.Filters
{
    public class StorageFilter : IProductFilter
    {
        public StorageFilter(StorageFilterType filterType, string value)
        {
            FilterType = (int)filterType;
            _filterType = filterType;
            Value = value;
        }

        public StorageFilter(FilterTable filter)
        {
            Id = filter.Id;
            FilterType = filter.FilterType;
            _filterType = (StorageFilterType)filter.FilterType;
            Value = filter.FilterVariat;
        }

        public int Id { get; set; }
        public ProductType ProductType => ProductType.STORAGE;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
        public string FilterFriendlyName
        {
            get
            {
                switch (_filterType)
                {
                    case StorageFilterType.Manufacturer:
                        return "Производитель";
                    case StorageFilterType.Size:
                        return "Объем";
                    case StorageFilterType.Sata:
                        return "Интерфейс";
                    case StorageFilterType.FormFactor:
                        return "Форм-фактор";
                    case StorageFilterType.Speed:
                        return "Скорость оборотов в минуту";
                }

                return "Другое";
            }
        }
        private readonly StorageFilterType _filterType;

        public bool IsSuitable(Product product)
        {
            var mfr = product.full_name.Split(' ').First().Trim();
            var size = product.description_list.FirstOrDefault(x => x.Contains(" ТБ", StringComparison.InvariantCultureIgnoreCase) || x.Contains(" ГБ", StringComparison.InvariantCultureIgnoreCase));
            var interfaceType = product.description_list.FirstOrDefault(x => x.Contains("SATA", StringComparison.InvariantCultureIgnoreCase) || x.Contains("PCI Express", StringComparison.InvariantCultureIgnoreCase));
            var formFactor = product.description_list.FirstOrDefault(x => x.Contains("M.2", StringComparison.InvariantCultureIgnoreCase) || x.Contains("2.5\"", StringComparison.InvariantCultureIgnoreCase));
            var speed = product.description_list.FirstOrDefault(x => x.Contains("об/мин", StringComparison.InvariantCultureIgnoreCase));

            switch (_filterType)
            {
                case StorageFilterType.Manufacturer:
                    return mfr == Value;
                case StorageFilterType.Sata:
                    return interfaceType == Value;
                case StorageFilterType.Speed:
                    return speed == Value;
                case StorageFilterType.Size:
                    return size == Value;
                case StorageFilterType.FormFactor:
                    return formFactor == Value;
            }

            return false;
        }
    }
}
