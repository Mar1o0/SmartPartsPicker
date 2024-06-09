using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Models.Filters
{
    public class MotherBoardFilter : IProductFilter
    {
        public MotherBoardFilter(MotherBoardFilterType filterType, string value)
        {
            FilterType = (int)filterType;
            _filterType = filterType;
            Value = value;
        }

        public MotherBoardFilter(FilterTable filter)
        {
            FilterType = filter.FilterType;
            _filterType = (MotherBoardFilterType)filter.FilterType;
            Value = filter.FilterVariat;
        }
        public ProductType ProductType => ProductType.MB;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
        public string FilterFriendlyName
        {
            get
            {
                switch (_filterType)
                {
                    case MotherBoardFilterType.Memory:
                        return "Объём памяти";
                    case MotherBoardFilterType.Manufacturer:
                        return "Производитель";
                    case MotherBoardFilterType.Soket:
                        return "Сокет";
                    case MotherBoardFilterType.Slots:
                        return "Слоты";
                    case MotherBoardFilterType.Chipset:
                        return "Чипсет";
                }

                return "Другое";
            }
        }
        private readonly MotherBoardFilterType _filterType;

        public bool IsSuitable(Product product)
        {
            var mfr = product.full_name.Split(' ').First();
            var chipset = product.description_list.FirstOrDefault(x => x.Contains(MB_CHIPSET_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var memory = product.description_list.FirstOrDefault(x => x.Contains(MB_MEMORY_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var soket = product.description_list.FirstOrDefault(x => x.Contains(MB_SOKET_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var slots = product.description_list.FirstOrDefault(x => x.Contains(MB_SLOTS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

            switch (_filterType)
            {
                case MotherBoardFilterType.Manufacturer:
                    return mfr == Value;
                case MotherBoardFilterType.Chipset:
                    return chipset == Value;
                case MotherBoardFilterType.Memory:
                    return memory == Value;
                case MotherBoardFilterType.Soket:
                    return soket == Value;
                case MotherBoardFilterType.Slots:
                    return slots == Value;
            }
            return false;
        }

        private const string MB_NAME_PREFIX = "Материнская плата";
        private const string MB_SOKET_DESCRIPTION_PATTERN = "сокет";
        private const string MB_CHIPSET_DESCRIPTION_PATTERN = "чипсет";
        private const string MB_MEMORY_DESCRIPTION_PATTERN = "память";
        private const string MB_SLOTS_DESCRIPTION_PATTERN = "слоты";
    }
}
