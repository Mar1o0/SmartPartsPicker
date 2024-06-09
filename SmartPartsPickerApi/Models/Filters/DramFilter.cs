using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Models.Filters
{
    public class DramFilter : IProductFilter
    {
        public DramFilter(DramFilterType filterType, string value)
        {
            FilterType = (int)filterType;
            _filterType = filterType;
            Value = value;
        }
        public ProductType ProductType => ProductType.RAM;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
        public string FilterFriendlyName
        {
            get
            {
                switch (_filterType)
                {
                    case DramFilterType.Rate:
                        return "Частота";
                    case DramFilterType.Manufacturer:
                        return "Производитель";
                    case DramFilterType.Size:
                        return "Объем памяти";
                    case DramFilterType.Voltage:
                        return "Напряжение";
                }

                return "Другое";
            }
        }
        private readonly DramFilterType _filterType;

        public bool IsSuitable(Product product)
        {
            throw new NotImplementedException();
        }
    }
}
