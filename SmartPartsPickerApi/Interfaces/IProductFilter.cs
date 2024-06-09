using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Interfaces
{
    public interface IProductFilter
    {
        public ProductType ProductType { get; }
        public int FilterType { get; }
        public string Value { get; }
        public bool IsSuitable(Product product);
    }
}
