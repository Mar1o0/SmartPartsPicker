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
            Value = value;
        }
        public ProductType ProductType => ProductType.GPU;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
    }
}
