using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

namespace SmartPartsPickerApi.Service.Filters
{
    public class DramFilterParser : IProductFilterParser
    {
        private const string DRAM_NAME_PREFIX = "Оперативная память";
        private const string DRAM_SIZE_DESCRIPTION_PATTERN = "ГБ";
        private const string DRAM_RATE_DESCRIPTION_PATTERN = "частота";
        private const string DRAM_VOLTAGE_DESCRIPTION_PATTERN = "напряжение";

        public ProductType ProductType => ProductType.RAM;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x => x.name_prefix == DRAM_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();

            var mfrVariants = new HashSet<string>();
            var sizeVariants = new HashSet<string>();
            var rateVariants = new HashSet<string>();
            var voltageVariants = new HashSet<string>();
            foreach (var item in productsToParse)
            {
                var mfr = item.full_name.Split(' ').First().Trim();
                var size = item.description_list.FirstOrDefault(x => x.Contains(DRAM_SIZE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var rate = item.description_list.FirstOrDefault(x => x.Contains(DRAM_RATE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var voltage = item.description_list.FirstOrDefault(x => x.Contains(DRAM_VOLTAGE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

                if (mfr != null && !mfrVariants.Contains(mfr))
                {
                    mfrVariants.Add(mfr);
                }
                if (size != null && !sizeVariants.Contains(size))
                {
                    sizeVariants.Add(size);
                }
                if (rate != null && !rateVariants.Contains(rate))
                {
                    rateVariants.Add(rate);
                }
                if (voltage != null && !voltageVariants.Contains(voltage))
                {
                    voltageVariants.Add(voltage);
                }
            }

            foreach (var mfr in mfrVariants)
            {
                availableFilters.Add(new DramFilter(DramFilterType.Manufacturer, mfr));
            }

            foreach (var size in sizeVariants)
            {
                availableFilters.Add(new DramFilter(DramFilterType.Size, size));
            }

            foreach (var rate in rateVariants)
            {
                availableFilters.Add(new DramFilter(DramFilterType.Rate, rate));
            }

            foreach (var voltage in voltageVariants)
            {
                availableFilters.Add(new DramFilter(DramFilterType.Voltage, voltage));
            }

            return availableFilters;
        }
    }
}
