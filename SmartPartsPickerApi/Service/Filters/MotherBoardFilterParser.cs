using SmartPartsPickerApi.Enums.Filters;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

namespace SmartPartsPickerApi.Service.Filters
{
    public class MotherBoardFilterParser : IProductFilterParser
    {
        private const string MB_NAME_PREFIX = "Материнская плата";
        private const string MB_SOKET_DESCRIPTION_PATTERN = "сокет";
        private const string MB_CHIPSET_DESCRIPTION_PATTERN = "чипсет";
        private const string MB_MEMORY_DESCRIPTION_PATTERN = "память";
        private const string MB_SLOTS_DESCRIPTION_PATTERN = "слоты";

        public ProductType ProductType => ProductType.MB;

        public List<IProductFilter> ParseFilters(List<Product> products)
        {
            var productsToParse = products.Where(x => x.name_prefix == MB_NAME_PREFIX).ToList();
            var availableFilters = new List<IProductFilter>();

            var soketVariants = new HashSet<string>(); // если не знаешь что это замени на лист
            var mfrVariants = new HashSet<string>();
            var chipsetVariants = new HashSet<string>();
            var memoryVariants = new HashSet<string>();
            var slotsVariants = new HashSet<string>();
            foreach (var item in productsToParse)
            {
                var mfr = item.full_name.Split(' ').First();
                var chipset = item.description_list.FirstOrDefault(x => x.Contains(MB_CHIPSET_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var memory = item.description_list.FirstOrDefault(x => x.Contains(MB_MEMORY_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var soket = item.description_list.FirstOrDefault(x => x.Contains(MB_SOKET_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));
                var slots = item.description_list.FirstOrDefault(x => x.Contains(MB_SLOTS_DESCRIPTION_PATTERN, StringComparison.InvariantCultureIgnoreCase));

                if (chipset != null && !soketVariants.Contains(chipset))
                {
                    soketVariants.Add(chipset);
                }
                if (mfr != null && !mfrVariants.Contains(mfr))
                {
                    mfrVariants.Add(mfr);
                }
                if (memory != null && !chipsetVariants.Contains(memory))
                {
                    chipsetVariants.Add(memory);
                }
                if (slots != null && !memoryVariants.Contains(slots))
                {
                    memoryVariants.Add(slots);
                }
                if (soket != null && !soketVariants.Contains(soket))
                {
                    soketVariants.Add(soket);
                }
            }

            foreach (var soket in soketVariants)
            {
                availableFilters.Add(new MotherBoardFilter(MotherBoardFilterType.Soket, soket)); // Так для всех
            }

            foreach (var mfr in mfrVariants)
            {
                availableFilters.Add(new MotherBoardFilter(MotherBoardFilterType.Manufacturer, mfr));
            }

            foreach (var chipset in chipsetVariants)
            {
                availableFilters.Add(new MotherBoardFilter(MotherBoardFilterType.Chipset, chipset));
            }
            foreach (var memory in memoryVariants)
            {
                availableFilters.Add(new MotherBoardFilter(MotherBoardFilterType.Memory, memory));
            }
            foreach (var slots in slotsVariants)
            {
                availableFilters.Add(new MotherBoardFilter(MotherBoardFilterType.Slots, slots));
            }

            return availableFilters;
        }
    }
}
