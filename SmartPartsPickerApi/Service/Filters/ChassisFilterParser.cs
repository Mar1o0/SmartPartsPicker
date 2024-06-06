using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

namespace SmartPartsPickerApi.Service.Filters
{
    public class ChassisFilterParser : IProductFilterParser
    {
        private const string CHASSIS_NAME_PREFIX = "Корпус";
        private const string CHASSIS_WIDTH_DESCRIPTION_PATTERN = "Tower";
        private const string CHASSIS_COUNT_FANS_DESCRIPTION_PATTERN = "в комплекте";
        private const string CHASSIS_SIZE_VIDEOCARD_DESCRIPTION_PATTERN = "видеокарта до";
        private const string CHASSIS_COLOR_DESCRIPTION_PATTERN = "цвет";

        public ProductType ProductType => ProductType.CHASSIS;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x => x.name_prefix == CHASSIS_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();

            var mfrVariants = new HashSet<string>();
            var widthVariants = new HashSet<string>();
            var countFansVariants = new HashSet<string>();
            var sizeVDVariants = new HashSet<string>();
            var colorVariants = new HashSet<string>();
            foreach (var item in productsToParse)
            {
                var mfr = item.full_name.Split(' ').First().Trim();
                var width = item.description_list.FirstOrDefault(x => x.Contains(CHASSIS_WIDTH_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var count = item.description_list.FirstOrDefault(x => x.Contains(CHASSIS_COUNT_FANS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var sizeVD = item.description_list.FirstOrDefault(x => x.Contains(CHASSIS_SIZE_VIDEOCARD_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var color = item.description_list.FirstOrDefault(x => x.Contains(CHASSIS_COLOR_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

                if (mfr != null && !mfrVariants.Contains(mfr))
                {
                    mfrVariants.Add(mfr);
                }
                if (width != null && !widthVariants.Contains(width))
                {
                    widthVariants.Add(width);
                }
                if (count != null && !countFansVariants.Contains(count))
                {
                    countFansVariants.Add(count);
                }
                if (sizeVD != null && !sizeVDVariants.Contains(sizeVD))
                {
                    sizeVDVariants.Add(sizeVD);
                }
                if (color != null && !colorVariants.Contains(color))
                {
                    colorVariants.Add(color);
                }
            }

            foreach (var mfr in mfrVariants)
            {
                availableFilters.Add(new ChassisFilter(ChassisFilterType.Manufacturer, mfr));
            }

            foreach (var width in widthVariants)
            {
                availableFilters.Add(new ChassisFilter(ChassisFilterType.Width, width));
            }

            foreach (var countFans in countFansVariants)
            {
                availableFilters.Add(new ChassisFilter(ChassisFilterType.Count_Fans, countFans));
            }

            foreach (var size in sizeVDVariants)
            {
                availableFilters.Add(new ChassisFilter(ChassisFilterType.Size_VideoCard, size));
            }
            foreach (var color in colorVariants)
            {
                availableFilters.Add(new ChassisFilter(ChassisFilterType.Color, color));
            }

            return availableFilters;
        }
    }
}
