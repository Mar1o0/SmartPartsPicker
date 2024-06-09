using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
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

        public DramFilter(FilterTable filter)
        {
            Id = filter.Id;
            FilterType = filter.FilterType;
            _filterType = (DramFilterType)filter.FilterType;
            Value = filter.FilterVariat;
        }

        public int Id { get; set; }
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
            var mfr = product.full_name.Split(' ').First().Trim();
            var size = product.description_list.FirstOrDefault(x => x.Contains(DRAM_SIZE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var rate = product.description_list.FirstOrDefault(x => x.Contains(DRAM_RATE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var voltage = product.description_list.FirstOrDefault(x => x.Contains(DRAM_VOLTAGE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

            switch (_filterType)
            {
                case DramFilterType.Manufacturer:
                    return mfr == Value;
                case DramFilterType.Size:
                    return size == Value;
                case DramFilterType.Rate:
                    return rate == Value;
                case DramFilterType.Voltage:
                    return voltage == Value;
            }
            return false;
        }

        private const string DRAM_NAME_PREFIX = "Оперативная память";
        private const string DRAM_SIZE_DESCRIPTION_PATTERN = "ГБ";
        private const string DRAM_RATE_DESCRIPTION_PATTERN = "частота";
        private const string DRAM_VOLTAGE_DESCRIPTION_PATTERN = "напряжение";
    }
}
