using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Onliner;
using System.Runtime.Intrinsics.Arm;
using System.Threading;
using SmartPartsPickerApi.Database.Tables;

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

        public PowerSupplyFilter(FilterTable filter)
        {
            FilterType = filter.FilterType;
            _filterType = (PowerSupplyFilterType)filter.FilterType;
            Value = filter.FilterVariat;
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

        public bool IsSuitable(Product product)
        {
            var mfr = product.full_name.Split(' ').First().Trim();
            var voltage = product.micro_description_list.FirstOrDefault(x => x.Contains(PSU_VOLTAGE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var qualify = product.description_list.FirstOrDefault(x => x.Contains(PSU_QUALIFY_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var fans = product.description_list.FirstOrDefault(x => x.Contains(PSU_FANS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

            switch (_filterType)
            {
                case PowerSupplyFilterType.Manufacturer:
                    return mfr == Value;
                case PowerSupplyFilterType.Voltage:
                    return voltage == Value;
                case PowerSupplyFilterType.Qualify:
                    return qualify == Value;
                case PowerSupplyFilterType.Fans:
                    return fans == Value;
            }
            return false;
        }

        private const string PSU_NAME_PREFIX = "Блок питания";
        private const string PSU_VOLTAGE_DESCRIPTION_PATTERN = "Вт";
        private const string PSU_QUALIFY_DESCRIPTION_PATTERN = "сертификат";
        private const string PSU_FANS_DESCRIPTION_PATTERN = "вентилятор";
    }
}
