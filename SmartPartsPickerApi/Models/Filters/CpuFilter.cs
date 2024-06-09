using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Onliner;

namespace SmartPartsPickerApi.Models.Filters
{
    public class CpuFilter : IProductFilter
    {

        private const string CPU_CORE_DESCRIPTION_PATTERN = "ядер";
        private const string CPU_THREADS_DESCRIPTION_PATTERN = "потоков";
        private const string CPU_TECH_PROCESS_DESCRIPTION_PATTERN = "техпроцесс";
        private const string CPU_TDP_DESCRIPTION_PATTERN = "TDP";

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
                    case CpuFilterType.Threads:
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

        public bool IsSuitable(Product product)
        {

            var mfr = product.full_name.Split(' ').First().Trim();
            var core = product.description_list.FirstOrDefault(x => x.Contains(CPU_CORE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var threads = product.description_list.FirstOrDefault(x => x.Contains(CPU_THREADS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var techProcess = product.description_list.FirstOrDefault(x => x.Contains(CPU_TECH_PROCESS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
            var tdp = product.description_list.FirstOrDefault(x => x.Contains(CPU_TDP_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));


            switch (_filterType) // так не делай нигде больше
            {
                case CpuFilterType.Manufacturer:
                    return mfr == Value;
                case CpuFilterType.Core:
                    return core == Value;
                case CpuFilterType.Threads:
                    return threads == Value;
                case CpuFilterType.TechProcess:
                    return techProcess == Value;
                case CpuFilterType.Tdp:
                    return tdp == Value;
            }
            return false;
        }
    }
}
