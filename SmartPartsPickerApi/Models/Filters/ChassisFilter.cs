using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Models.Filters
{
    public class ChassisFilter : IProductFilter
    {
        public ChassisFilter(ChassisFilterType filterType, string value)
        {
            FilterType = (int)filterType;
            _filterType = filterType;
            Value = value;
        }
        public ProductType ProductType => ProductType.CHASSIS;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
        public string FilterFriendlyName
        {
            get
            {
                switch (_filterType)
                {
                    case ChassisFilterType.Width:
                        return "Размер корпуса";
                    case ChassisFilterType.Color:
                        return "Цвет";
                    case ChassisFilterType.Count_Fans:
                        return "Количество вентиляторов";
                    case ChassisFilterType.Size_VideoCard:
                        return "Видеокарта до";
                    case ChassisFilterType.Manufacturer:
                        return "Производитель";
                }

                return "Другое";
            }
        }
        private readonly ChassisFilterType _filterType;
    }
}
