using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;
using System.Text.RegularExpressions;

namespace SmartPartsPickerApi.Service.Filters
{
    public class CpuFilterParser : IProductFilterParser
    {
        private const string CPU_NAME_PREFIX = "Процессор";

        public ProductType ProductType => ProductType.CPU;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x => x.name_prefix == CPU_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();

            var mfrVariants = new HashSet<string>();
            var coreVariants = new HashSet<int>();
            var flowVariants = new HashSet<int>();
            var techProcessVariants = new HashSet<int>();
            var tdpVariants = new HashSet<int>();
            var socketVariants = new HashSet<string>();

            foreach (var item in productsToParse)
            {
                var mfr = item.full_name.Split(' ').First().Trim();
                mfrVariants.Add(mfr);

                foreach (var description in item.description_list)
                {
                    ParseCoreAndThreads(description, coreVariants, flowVariants);
                    ParseTechProcess(description, techProcessVariants);
                    ParseTdp(description, tdpVariants);
                    ParseSocket(description, socketVariants);
                }
            }

            AddFilters(availableFilters, mfrVariants, CpuFilterType.Manufacturer);
            AddFilters(availableFilters, coreVariants, CpuFilterType.Core);
            AddFilters(availableFilters, flowVariants, CpuFilterType.Threads);
            AddFilters(availableFilters, techProcessVariants, CpuFilterType.TechProcess);
            AddFilters(availableFilters, tdpVariants, CpuFilterType.Tdp);
            AddFilters(availableFilters, socketVariants, CpuFilterType.Socket);

            return availableFilters;
        }

        private void AddFilters<T>(List<IProductFilter> filters, HashSet<T> values, CpuFilterType filterType)
        {
            foreach (var value in values)
            {
                filters.Add(new CpuFilter(filterType, value.ToString()));
            }
        }

        private void ParseCoreAndThreads(string description, HashSet<int> coreVariants, HashSet<int> flowVariants)
        {
            var match = Regex.Match(description, @"(\d+)\s*ядер(?:,\s*(\d+)\s*потоков)?");
            if (match.Success)
            {
                coreVariants.Add(int.Parse(match.Groups[1].Value));
                if (match.Groups[2].Success)
                {
                    flowVariants.Add(int.Parse(match.Groups[2].Value));
                }
            }
        }

        private void ParseFrequency(string description, HashSet<(float, float)> frequencyVariants)
        {
            var match = Regex.Match(description, @"частота\s+(\d+(?:\.\d+)?)\/(?:(\d+(?:\.\d+)?)\s+)?ГГц");
            if (match.Success)
            {
                var minFreq = float.Parse(match.Groups[1].Value);
                var maxFreq = match.Groups[2].Success ? float.Parse(match.Groups[2].Value) : minFreq;
                frequencyVariants.Add((minFreq, maxFreq));
            }
        }

        private void ParseCache(string description, HashSet<(int, int)> cacheVariants)
        {
            var match = Regex.Match(description, @"кэш\s+(\d+)\s+МБ\s*\+\s*(\d+)\s+МБ");
            if (match.Success)
            {
                cacheVariants.Add((int.Parse(match.Groups[1].Value), int.Parse(match.Groups[2].Value)));
            }
        }

        private void ParseTechProcess(string description, HashSet<int> techProcessVariants)
        {
            var match = Regex.Match(description, @"техпроцесс\s+(\d+)\s+нм");
            if (match.Success)
            {
                techProcessVariants.Add(int.Parse(match.Groups[1].Value));
            }
        }

        private void ParseTdp(string description, HashSet<int> tdpVariants)
        {
            var match = Regex.Match(description, @"TDP\s+(\d+)\s*W");
            if (match.Success)
            {
                tdpVariants.Add(int.Parse(match.Groups[1].Value));
            }
        }

        private void ParseSocket(string description, HashSet<string> socketVariants)
        {
            var match = Regex.Match(description, @"^(LGA|AM)\d+$");
            if (match.Success)
            {
                socketVariants.Add(description);
            }
        }
    }
}
