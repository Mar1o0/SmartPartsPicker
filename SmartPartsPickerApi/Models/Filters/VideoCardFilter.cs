using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Models.Filters
{
    public class VideoCardFilter : IProductFilter
    {

        public VideoCardFilter(VideoCardFilterType filterType, string value)
        {
            FilterType = (int)filterType;
            _filterType = filterType;
            Value = value;
        }
        public ProductType ProductType => ProductType.GPU;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
        public string FilterFriendlyName
        {
            get
            {
                switch (_filterType)
                {
                    case VideoCardFilterType.VRAM:
                        return "Объём видео памяти";
                    case VideoCardFilterType.Manufacturer:
                        return "Производитель";
                    case VideoCardFilterType.Width:
                        return "Количество слотов";
                    case VideoCardFilterType.RT:
                        return "Трассировка лучей";
                    case VideoCardFilterType.Fans:
                        return "Количество вентиляторов";
                }

                return "Другое";
            }
        }
        private readonly VideoCardFilterType _filterType;
    }
}
