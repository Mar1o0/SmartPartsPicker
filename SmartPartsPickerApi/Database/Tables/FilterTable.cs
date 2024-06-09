using LinqToDB.Mapping;
using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "Filter")]
    public class FilterTable
    {
        [Column(Name = "id", IsPrimaryKey = true)]
        public int Id { get; set; }
        [Column(Name = "filterType")]
        public int FilterType { get; set; } // TODO: FilterType
        [Column(Name = "productType")]
        public ProductType ProductType { get; set; }
        [Column(Name = "filterVariat")]
        public int FilterVariat { get; set; }
    }
}
