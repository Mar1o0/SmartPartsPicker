using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Interfaces
{
    public interface IProductFilterParser
    {
        public ProductType ProductType { get; }
        public List<IProductFilter> ParseFilters(List<Product> productResp);
    }
}
