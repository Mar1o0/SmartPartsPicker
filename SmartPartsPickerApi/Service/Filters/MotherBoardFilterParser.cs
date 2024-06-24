using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;
using System.Text.RegularExpressions;

namespace SmartPartsPickerApi.Service.Filters
{
    public class MotherBoardFilterParser : IProductFilterParser
    {
        private const string MB_NAME_PREFIX = "Материнская плата";

        public ProductType ProductType => ProductType.MB;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x => x.name_prefix == MB_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();

            var socketVariants = new HashSet<string>();
            var mfrVariants = new HashSet<string>();
            var chipsetVariants = new HashSet<string>();
            var memoryVariants = new HashSet<string>();
            var slotsVariants = new HashSet<string>();
            var formFactorVariants = new HashSet<string>();
            foreach (var item in productsToParse)
            {
                mfrVariants.Add(item.full_name.Split(' ').First());
                foreach (var description in item.description_list)
                {
                    ParseSocket(description, socketVariants);
                    ParseChipset(description, chipsetVariants);
                    ParseMemory(description, memoryVariants);
                    ParseSlots(description, slotsVariants);
                }
            }

            AddFilters(availableFilters, socketVariants, MotherBoardFilterType.Socket);
            AddFilters(availableFilters, mfrVariants, MotherBoardFilterType.Manufacturer);
            AddFilters(availableFilters, chipsetVariants, MotherBoardFilterType.Chipset);
            AddFilters(availableFilters, memoryVariants, MotherBoardFilterType.Memory);
            AddFilters(availableFilters, slotsVariants, MotherBoardFilterType.Slots);

            return availableFilters;
        }
        private void AddFilters<T>(List<IProductFilter> filters, HashSet<T> values, MotherBoardFilterType filterType)
        {
            foreach (var value in values)
            {
                filters.Add(new MotherBoardFilter(filterType, value.ToString()));
            }
        }

        private void ParseSocket(string description, HashSet<string> socketVariants)
        {
            var match = Regex.Match(description, @"сокет\s+(?<socket>(?:AMD|Intel)\s+[^,]+)");
            if (match.Success)
            {
                socketVariants.Add(match.Groups["socket"].Value.Trim());
            }
        }

        private void ParseChipset(string description, HashSet<string> chipsetVariants)
        {
            var match = Regex.Match(description, @"чипсет\s+(?<chipset>(?:AMD|Intel)\s+[^,]+)");
            if (match.Success)
            {
                chipsetVariants.Add(match.Groups["chipset"].Value.Trim());
            }
        }

        private void ParseMemory(string description, HashSet<string> memoryTypeVariants)
        {
            var match = Regex.Match(description, @"(?<type>DDR\d)");
            if (match.Success)
            {
                memoryTypeVariants.Add(match.Groups["type"].Value);
            }
        }

        private void ParseSlots(string description, HashSet<string> pcieSlotsVariants)
        {
            var match = Regex.Match(description, @"слоты:\s+(?<slots>.+)");
            if (match.Success)
            {
                var slotsDescription = match.Groups["slots"].Value;
                // Extract PCIe slots information
                var pcieMatch = Regex.Match(slotsDescription, @"(?<count>\d+)xPCIe\s+x(?<width>\d+)\s+(?<version>\d+(?:\.\d+)?)");
                while (pcieMatch.Success)
                {
                    pcieSlotsVariants.Add($"{pcieMatch.Groups["count"].Value}xPCIe x{pcieMatch.Groups["width"].Value} {pcieMatch.Groups["version"].Value}");
                    pcieMatch = pcieMatch.NextMatch();
                }
            }
        }
    }
}
