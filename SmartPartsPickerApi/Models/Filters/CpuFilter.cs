using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;
using System.Text.RegularExpressions;

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

        public CpuFilter(FilterTable filter)
        {
            Id = filter.Id;
            FilterType = filter.FilterType;
            _filterType = (CpuFilterType)filter.FilterType;
            Value = filter.FilterVariat;
        }

        public int Id { get; set; }

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
                        return "Энергопотребление";
                    case CpuFilterType.Socket:
                        return "Сокет";
                }

                return "Другое";
            }
        }

        private readonly CpuFilterType _filterType;

        public bool IsSuitable(Product product)
        {
            switch (_filterType)
            {
                case CpuFilterType.Manufacturer:
                    return product.full_name.Split(' ').First().Trim() == Value;
                case CpuFilterType.Core:
                    return GetCores(product) == Value;
                case CpuFilterType.Threads:
                    return GetThreads(product) == Value;
                case CpuFilterType.TechProcess:
                    return GetTechProcess(product) == Value;
                case CpuFilterType.Tdp:
                    return GetTdp(product) == Value;
                case CpuFilterType.Socket:
                    return GetSocket(product) == Value;
            }

            return false;
        }

        private string? GetCores(Product product)
        {
            foreach (var description in product.description_list)
            {
                var match = Regex.Match(description, @"(\d+)\s*ядер");
                if (match.Success)
                {
                    return match.Groups[1].Value;
                }
            }

            return null;
        }

        private string? GetThreads(Product product)
        {
            foreach (var description in product.description_list)
            {
                var match = Regex.Match(description, @"(\d+)\s*потоков");
                if (match.Success)
                {
                    return match.Groups[1].Value;
                }
            }

            return null;
        }
        private string? GetTechProcess(Product product)
        {
            foreach (var description in product.description_list)
            {
                var match = Regex.Match(description, @"техпроцесс\s+(\d+)\s+нм");
                if (match.Success)
                {
                    return match.Groups[1].Value;
                }
            }

            return null;
        }

        private string? GetTdp(Product product)
        {
            foreach (var description in product.description_list)
            {
                var match = Regex.Match(description, @"TDP\s+(\d+)\s*W");
                if (match.Success)
                {
                    return match.Groups[1].Value;
                }
            }

            return null;
        }

        private string GetSocket(Product product)
        {
            foreach (var description in product.description_list)
            {
                var match = Regex.Match(description, @"^(LGA|AM)\d+$");
                if (match.Success)
                {
                    return description;
                }
            }

            return null;
        }
    }
}
