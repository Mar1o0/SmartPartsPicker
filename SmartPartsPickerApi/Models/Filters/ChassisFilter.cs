using SmartPartsPickerApi.Database.Tables;
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

        public ChassisFilter(FilterTable filter)
        {
            Id = filter.Id;
            FilterType = filter.FilterType;
            _filterType = (ChassisFilterType)filter.FilterType;
            Value = filter.FilterVariat;
        }

        public int Id { get; set; }
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

        public bool IsSuitable(Product product)
        {
            var mfr = product.full_name.Split(' ').First().Trim();
            var width = product.description_list.FirstOrDefault(x => x.Contains(CHASSIS_WIDTH_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var count = product.description_list.FirstOrDefault(x => x.Contains(CHASSIS_COUNT_FANS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var sizeVD = product.description_list.FirstOrDefault(x => x.Contains(CHASSIS_SIZE_VIDEOCARD_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var color = product.description_list.FirstOrDefault(x => x.Contains(CHASSIS_COLOR_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

            switch (_filterType) // так не делай нигде больше
            {
                case ChassisFilterType.Manufacturer:
                    return mfr == Value;
                case ChassisFilterType.Width:
                    return width == Value;
                case ChassisFilterType.Count_Fans:
                    return count == Value;
                case ChassisFilterType.Size_VideoCard:
                    return sizeVD == Value;
                case ChassisFilterType.Color:
                    return color == Value;
            }
            return false;

        }

        private const string CHASSIS_NAME_PREFIX = "Корпус";
        private const string CHASSIS_WIDTH_DESCRIPTION_PATTERN = "Tower";
        private const string CHASSIS_COUNT_FANS_DESCRIPTION_PATTERN = "в комплекте";
        private const string CHASSIS_SIZE_VIDEOCARD_DESCRIPTION_PATTERN = "видеокарта до";
        private const string CHASSIS_COLOR_DESCRIPTION_PATTERN = "цвет";
    }
}
