using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;
using System.Text.RegularExpressions;

namespace SmartPartsPickerApi.Models.Filters
{
    public class MotherBoardFilter : IProductFilter
    {
        public MotherBoardFilter(MotherBoardFilterType filterType, string value)
        {
            FilterType = (int)filterType;
            _filterType = filterType;
            Value = value;
        }

        public MotherBoardFilter(FilterTable filter)
        {
            Id = filter.Id;
            FilterType = filter.FilterType;
            _filterType = (MotherBoardFilterType)filter.FilterType;
            Value = filter.FilterVariat;
        }
        public int Id { get; set; }
        public ProductType ProductType => ProductType.MB;

        public int FilterType { get; private set; }

        public string Value { get; private set; }
        public string FilterFriendlyName
        {
            get
            {
                switch (_filterType)
                {
                    case MotherBoardFilterType.Memory:
                        return "Тип памяти";
                    case MotherBoardFilterType.Manufacturer:
                        return "Производитель";
                    case MotherBoardFilterType.Socket:
                        return "Сокет";
                    case MotherBoardFilterType.Slots:
                        return "Слоты";
                    case MotherBoardFilterType.Chipset:
                        return "Чипсет";
                }

                return "Другое";
            }
        }
        private readonly MotherBoardFilterType _filterType;

        public bool IsSuitable(Product product)
        {
            switch (_filterType)
            {
                case MotherBoardFilterType.Manufacturer:
                    return product.full_name.Split(' ').First().Trim() == Value;
                case MotherBoardFilterType.Chipset:
                    return GetChipset(product) == Value;
                case MotherBoardFilterType.Memory:
                    return GetMemoryType(product) == Value;
                case MotherBoardFilterType.Socket:
                    return GetSocket(product) == Value;
                case MotherBoardFilterType.Slots:
                    return GetPcieSlots(product).Contains(Value);
            }

            return false;
        }

        private string GetSocket(Product product)
        {
            foreach (var description in product.description_list)
            {
                var match = Regex.Match(description, @"сокет\s+(?<socket>(?:AMD|Intel)\s+[^,]+)");
                if (match.Success)
                {
                    return match.Groups["socket"].Value.Trim();
                }
            }

            return null;
        }

        private string GetChipset(Product product)
        {
            foreach (var description in product.description_list)
            {
                var match = Regex.Match(description, @"чипсет\s+(?<chipset>(?:AMD|Intel)\s+[^,]+)");
                if (match.Success)
                {
                    return match.Groups["chipset"].Value.Trim();
                }
            }

            return null;
        }

        private string GetMemoryType(Product product)
        {
            foreach (var description in product.description_list)
            {
                var match = Regex.Match(description, @"(?<type>DDR\d)");
                if (match.Success)
                {
                    return match.Groups["type"].Value;
                }
            }

            return null;
        }
        private List<string> GetPcieSlots(Product product)
        {
            var pcieSlots = new List<string>();
            foreach (var description in product.description_list)
            {
                var match = Regex.Match(description, @"слоты:\s+(?<slots>.+)");
                if (match.Success)
                {
                    var slotsDescription = match.Groups["slots"].Value;
                    var pcieMatch = Regex.Match(slotsDescription, @"(?<count>\d+)xPCIe\s+x(?<width>\d+)\s+(?<version>\d+(?:\.\d+)?)");
                    while (pcieMatch.Success)
                    {
                        pcieSlots.Add($"{pcieMatch.Groups["count"].Value}xPCIe x{pcieMatch.Groups["width"].Value} {pcieMatch.Groups["version"].Value}");
                        pcieMatch = pcieMatch.NextMatch();
                    }
                }
            }

            return pcieSlots;
        }
    }
}
