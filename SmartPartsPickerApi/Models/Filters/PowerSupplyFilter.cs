using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Models.Filters
{
    public class PowerSupplyFilter : IProductFilter
    {
        public PowerSupplyFilter(PowerSupplyFilterType filterType, string value)
        {
            FilterType = (int)filterType;
            _filterType = filterType;
            Value = value;
        }
        public ProductType ProductType => ProductType.PSU;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
        public string FilterFriendlyName
        {
            get
            {
                switch (_filterType)
                {
                    case PowerSupplyFilterType.Fans:
                        return "Вертиляторы";
                    case PowerSupplyFilterType.Manufacturer:
                        return "Производитель";
                    case PowerSupplyFilterType.Qualify:
                        return "Сертификат";
                    case PowerSupplyFilterType.Voltage:
                        return "Напряжение";
                }

                return "Другое";
            }
        }
        private readonly PowerSupplyFilterType _filterType;
    }
}
