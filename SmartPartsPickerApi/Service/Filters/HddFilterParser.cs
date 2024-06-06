using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

namespace SmartPartsPickerApi.Service.Filters
{
    public class HddFilterParser : IProductFilterParser
    {
        private const string HDD_NAME_PREFIX = "Жесткий диск";
        private const string HDD_SATA_DESCRIPTION_PATTERN = "SATA";
        private const string HDD_SPEED_DESCRIPTION_PATTERN = "об/мин";

        public ProductType ProductType => ProductType.HDD;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x => x.name_prefix == HDD_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();

            var mfrVariants = new HashSet<string>();
            var sataVariants = new HashSet<string>();
            var speedVariants = new HashSet<string>();

            foreach (var item in productsToParse)
            {
                var mfr = item.full_name.Split(' ').First().Trim();
                var sata = item.description_list.FirstOrDefault(x => x.Contains(HDD_SATA_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var speed = item.description_list.FirstOrDefault(x => x.Contains(HDD_SPEED_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                
                if (mfr != null && !mfrVariants.Contains(mfr))
                {
                    mfrVariants.Add(mfr);
                }
                if (sata != null && !sataVariants.Contains(sata))
                {
                    sataVariants.Add(sata);
                }
                if (speed != null && !speedVariants.Contains(speed))
                {
                    speedVariants.Add(speed);
                }
            }

            foreach (var mfr in mfrVariants)
            {
                availableFilters.Add(new HddFilter(HddFilterType.Manufacturer, mfr));
            }

            foreach (var sata in sataVariants)
            {
                availableFilters.Add(new HddFilter(HddFilterType.Sata, sata));
            }

            foreach (var speed in speedVariants)
            {
                availableFilters.Add(new HddFilter(HddFilterType.Speed, speed));
            }

            return availableFilters;
        }
    }
}
