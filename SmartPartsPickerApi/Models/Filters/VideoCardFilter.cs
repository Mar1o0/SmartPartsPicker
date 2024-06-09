using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Onliner;
using System.Runtime.Intrinsics.Arm;
using System.Threading;

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

        public VideoCardFilter(FilterTable filter)
        {
            FilterType = filter.FilterType;
            _filterType = (VideoCardFilterType)filter.FilterType;
            Value = filter.FilterVariat;
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

        public bool IsSuitable(Product product)
        {
            var mfr = product.full_name.Split(' ').First();
            var vram = product.description_list.FirstOrDefault(x => x.Contains(VC_VRAM_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var width = product.description_list.FirstOrDefault(x => x.Contains(VC_WIDTH_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var fans = product.micro_description_list.FirstOrDefault(x => x.Contains(VC_FANS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var rt = product.description_list.Contains(VC_RT_SUPPORT_DESCRIPTION);

            switch (_filterType)
            {
                case VideoCardFilterType.Manufacturer:
                    return mfr == Value;
                case VideoCardFilterType.VRAM:
                    return vram == Value;
                case VideoCardFilterType.Width:
                    return width == Value;
                case VideoCardFilterType.Fans:
                    return fans == Value;
                case VideoCardFilterType.RT:
                    return rt.ToString() == Value;
            }
            return false;
        }

        private const string VC_NAME_PREFIX = "Видеокарта";
        private const string VC_RT_SUPPORT_DESCRIPTION = "трассировка лучей";
        private const string VC_VRAM_DESCRIPTION_PATTERN = "DDR";
        private const string VC_WIDTH_DESCRIPTION_PATTERN = "слот";
        private const string VC_FANS_DESCRIPTION_PATTERN = "вентиляторов";
    }
}
