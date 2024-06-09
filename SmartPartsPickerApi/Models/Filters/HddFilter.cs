using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Models.Filters
{
    public class HddFilter : IProductFilter
    {
        public HddFilter(HddFilterType filterType, string value)
        {
            FilterType = (int)filterType;
            _filterType = filterType;
            Value = value;
        }

        public HddFilter(FilterTable filter)
        {
            FilterType = filter.FilterType;
            _filterType = (HddFilterType)filter.FilterType;
            Value = filter.FilterVariat;
        }

        public ProductType ProductType => ProductType.HDD;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
        public string FilterFriendlyName
        {
            get
            {
                switch (_filterType)
                {
                    case HddFilterType.Sata:
                        return "SATA";
                    case HddFilterType.Manufacturer:
                        return "Производитель";
                    case HddFilterType.Speed:
                        return "Скорость оборотов в минуту";
                }

                return "Другое";
            }
        }
        private readonly HddFilterType _filterType;

        public bool IsSuitable(Product product)
        {
            var mfr = product.full_name.Split(' ').First().Trim();
            var sata = product.description_list.FirstOrDefault(x => x.Contains(HDD_SATA_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var speed = product.description_list.FirstOrDefault(x => x.Contains(HDD_SPEED_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

            switch (_filterType)
            {
                case HddFilterType.Manufacturer:
                    return mfr == Value;
                case HddFilterType.Sata:
                    return sata == Value;
                case HddFilterType.Speed:
                    return speed == Value;
            }
            return false;
        }

        private const string HDD_NAME_PREFIX = "Жесткий диск";
        private const string HDD_SATA_DESCRIPTION_PATTERN = "SATA";
        private const string HDD_SPEED_DESCRIPTION_PATTERN = "об/мин";
    }
}
