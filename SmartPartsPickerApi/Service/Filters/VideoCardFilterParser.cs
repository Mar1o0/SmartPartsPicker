using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

namespace SmartPartsPickerApi.Service.Filters
{
    public class VideoCardFilterParser : IProductFilterParser
    {
        private const string VC_NAME_PREFIX = "Видеокарта";
        private const string VC_RT_SUPPORT_DESCRIPTION = "трассировка лучей";
        private const string VC_VRAM_DESCRIPTION_PATTERN = "DDR";
        private const string VC_WIDTH_DESCRIPTION_PATTERN = "слот";
        private const string VC_FANS_DESCRIPTION_PATTERN = "вентиляторов";

        public ProductType ProductType => ProductType.GPU;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x=>x.name_prefix == VC_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();


            var vramVariants = new HashSet<string>(); // если не знаешь что это замени на лист
            var mfrVariants = new HashSet<string>();
            var widthVariants = new HashSet<string>();
            var fansVariants = new HashSet<string>();
            foreach (var item in productsToParse)
            {
                var vram = item.description_list.FirstOrDefault(x => x.Contains(VC_VRAM_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var mfr = item.extended_name.Split(' ').First();
                var width = item.description_list.FirstOrDefault(x => x.Contains(VC_WIDTH_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var fans = item.micro_description_list.FirstOrDefault(x => x.Contains(VC_FANS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

                if(vram != null && !vramVariants.Contains(vram))
                {
                    vramVariants.Add(vram);
                }
                if(mfr != null && !mfrVariants.Contains(mfr))
                {
                    mfrVariants.Add(mfr);
                }
                if(width != null && !widthVariants.Contains(width))
                {
                    widthVariants.Add(width);
                }
                if(fans != null && !fansVariants.Contains(fans))
                {
                    fansVariants.Add(fans);
                }
            }

            if (productsToParse.Any(x => x.description_list.Contains(VC_RT_SUPPORT_DESCRIPTION)))
            {
                var RT = new VideoCardFilter(VideoCardFilterType.RT, VC_RT_SUPPORT_DESCRIPTION);
                availableFilters.Add(RT);
            }

            foreach (var vram in vramVariants)
            {
                availableFilters.Add(new VideoCardFilter(VideoCardFilterType.VRAM, vram)); // Так для всех
            }


            return availableFilters;
        }

    }
}
