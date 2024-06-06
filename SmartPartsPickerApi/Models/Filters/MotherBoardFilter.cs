using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
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
    }
}
