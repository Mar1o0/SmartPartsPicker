using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Models.Filters
{
    public class CpuFilter : IProductFilter
    {
        public CpuFilter(CpuFilterType filterType, string value)
        {
            FilterType = (int)filterType;
            _filterType = filterType;
            Value = value;
        }
        public ProductType ProductType => ProductType.CPU;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
        public string FilterFriendlyName
        {
            get
            {
                switch (_filterType)
                {
                    case CpuFilterType.Manufacturer:
                        return "Производитель";
                    case CpuFilterType.Core:
                        return "Количество ядер";
                    case CpuFilterType.Flow:
                        return "Количество потоков";
                    case CpuFilterType.TechProcess:
                        return "Техпроцесс";
                    case CpuFilterType.Tdp:
                        return "TDP";
                }

                return "Другое";
            }
        }
        private readonly CpuFilterType _filterType;
    }
}
