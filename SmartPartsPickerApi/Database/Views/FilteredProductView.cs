using LinqToDB.Mapping;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Database.Views
{
    public class FilteredProductView : ProductTable
    {

        [Column(Name = "href")]
        public string? Image { get; set; }
        [NotColumn]
        public List<ProductPriceTable> Price { get; set; }
        [NotColumn]
        public List<IProductFilter>? Specs { get; set; }
    }
}
