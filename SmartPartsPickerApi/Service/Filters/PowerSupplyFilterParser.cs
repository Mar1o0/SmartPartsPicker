using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

namespace SmartPartsPickerApi.Service.Filters
{
    public class PowerSupplyFilterParser : IProductFilterParser
    {
        private const string PSU_NAME_PREFIX = "Блок питания";
        private const string PSU_VOLTAGE_DESCRIPTION_PATTERN = "Вт";
        private const string PSU_QUALIFY_DESCRIPTION_PATTERN = "сертификат";
        private const string PSU_FANS_DESCRIPTION_PATTERN = "вентилятор";

        public ProductType ProductType => ProductType.PSU;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x => x.name_prefix == PSU_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();

            var mfrVariants = new HashSet<string>();
            var voltageVariants = new HashSet<string>();
            var qualifyVariants = new HashSet<string>();
            var fansVariants = new HashSet<string>();
            foreach (var item in productsToParse)
            {
                var mfr = item.full_name.Split(' ').First().Trim();
                var voltage = item.micro_description_list.FirstOrDefault(x => x.Contains(PSU_VOLTAGE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var qualify = item.description_list.FirstOrDefault(x => x.Contains(PSU_QUALIFY_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var fans = item.description_list.FirstOrDefault(x => x.Contains(PSU_FANS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

                if (mfr != null && !mfrVariants.Contains(mfr))
                {
                    mfrVariants.Add(mfr);
                }
                if (voltage != null && !voltageVariants.Contains(voltage))
                {
                    voltageVariants.Add(voltage);
                }
                if (qualify != null && !qualifyVariants.Contains(qualify))
                {
                    qualifyVariants.Add(qualify);
                }
                if (fans != null && !fansVariants.Contains(fans))
                {
                    fansVariants.Add(fans);
                }
            }

            foreach (var mfr in mfrVariants)
            {
                availableFilters.Add(new PowerSupplyFilter(PowerSupplyFilterType.Manufacturer, mfr));
            }

            foreach (var voltage in voltageVariants)
            {
                availableFilters.Add(new PowerSupplyFilter(PowerSupplyFilterType.Voltage, voltage));
            }

            foreach (var qualify in qualifyVariants)
            {
                availableFilters.Add(new PowerSupplyFilter(PowerSupplyFilterType.Qualify, qualify));
            }

            foreach (var fans in fansVariants)
            {
                availableFilters.Add(new PowerSupplyFilter(PowerSupplyFilterType.Fans, fans));
            }

            return availableFilters;
        }
    }
}
