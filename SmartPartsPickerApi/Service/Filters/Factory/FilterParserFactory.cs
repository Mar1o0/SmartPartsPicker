using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Service.Filters.Factory
{

    class FilterParserFactory : IProductFilterParser // это паттерн фабрика, знакомимся
    {

        public FilterParserFactory(ProductType type)
        {

            switch (type)
            {
                case ProductType.GPU:
                    _impl = new VideoCardFilterParser();
                    break;
                case ProductType.CHASSIS:
                    _impl = new ChassisFilterParser();
                    break;
                case ProductType.CPU:
                    _impl = new CpuFilterParser();
                    break;
                case ProductType.RAM:
                    _impl = new DramFilterParser();
                    break;
                case ProductType.HDD:
                    _impl = new HddFilterParser();
                    break;
                case ProductType.MB:
                    _impl = new MotherBoardFilterParser();
                    break;
                case ProductType.PSU:
                    _impl = new PowerSupplyFilterParser();
                    break;
                case ProductType.SSD:
                    _impl = null;
                    break;
            }
            if (_impl == null)
            {
                throw new InvalidDataException("Not realized product filter");
            }
        }

        public ProductType ProductType => _impl.ProductType;

        public List<IProductFilter> ParseFilters(List<Product> productResp)
        {
            return _impl.ParseFilters(productResp);
        }

        private IProductFilterParser _impl;
    }
}
