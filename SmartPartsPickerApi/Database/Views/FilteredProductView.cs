using LinqToDB.Mapping;
using SmartPartsPickerApi.Database.Tables;

namespace SmartPartsPickerApi.Database.Views
{
    public class FilteredProductView : ProductTable
    {

        [Column(Name = "href")]
        public string? Image { get; set; }
    }
}
