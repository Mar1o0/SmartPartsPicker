using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
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
            throw new NotImplementedException();
        }
    }
}
