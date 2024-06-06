using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

namespace SmartPartsPickerApi.Service.Filters
{
    public class CpuFilterParser : IProductFilterParser
    {
        private const string CPU_NAME_PREFIX = "Процессор";
        private const string CPU_CORE_DESCRIPTION_PATTERN = "ядер";
        private const string CPU_FLOW_DESCRIPTION_PATTERN = "потоков";
        private const string CPU_TECH_PROCESS_DESCRIPTION_PATTERN = "техпроцесс";
        private const string CPU_TDP_DESCRIPTION_PATTERN = "TDP";

        public ProductType ProductType => ProductType.CPU;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x => x.name_prefix == CPU_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();

            var mfrVariants = new HashSet<string>();
            var coreVariants = new HashSet<string>();
            var flowVariants = new HashSet<string>();
            var techProcessVariants = new HashSet<string>();
            var tdpVariants = new HashSet<string>();
            foreach (var item in productsToParse)
            {
                var mfr = item.full_name.Split(' ').First().Trim();
                var core = item.description_list.FirstOrDefault(x => x.Contains(CPU_CORE_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var flow = item.description_list.FirstOrDefault(x => x.Contains(CPU_FLOW_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var techProcess = item.description_list.FirstOrDefault(x => x.Contains(CPU_TECH_PROCESS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var tdp = item.description_list.FirstOrDefault(x => x.Contains(CPU_TDP_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

                if (mfr != null && !mfrVariants.Contains(mfr))
                {
                    mfrVariants.Add(mfr);
                }
                if (core != null && !coreVariants.Contains(core))
                {
                    coreVariants.Add(core);
                }
                if (flow != null && !flowVariants.Contains(flow))
                {
                    flowVariants.Add(flow);
                }
                if (techProcess != null && !techProcessVariants.Contains(techProcess))
                {
                    techProcessVariants.Add(techProcess);
                }
                if (tdp != null && !tdpVariants.Contains(tdp))
                {
                    tdpVariants.Add(tdp);
                }
            }

            foreach (var mfr in mfrVariants)
            {
                availableFilters.Add(new CpuFilter(CpuFilterType.Manufacturer, mfr));
            }

            foreach (var core in coreVariants)
            {
                availableFilters.Add(new CpuFilter(CpuFilterType.Core, core));
            }

            foreach (var flow in flowVariants)
            {
                availableFilters.Add(new CpuFilter(CpuFilterType.Flow, flow));
            }

            foreach (var techProcess in techProcessVariants)
            {
                availableFilters.Add(new CpuFilter(CpuFilterType.TechProcess, techProcess));
            }
            foreach (var tdp in tdpVariants)
            {
                availableFilters.Add(new CpuFilter(CpuFilterType.Tdp, tdp));
            }

            return availableFilters;
        }
    }
}
