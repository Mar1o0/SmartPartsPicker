using LinqToDB.Mapping;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "Filter")]
    public class FilterTable
    {
        [Column(Name = "id", IsPrimaryKey = true)]
        public int Id { get; set; }

        [Column(Name = "productType")]
        public ProductType ProductType { get; set; }

        [Column(Name = "filterType")]
        public int FilterType { get; set; } // FilterType

        [Column(Name = "filterVariat")]
        public string FilterVariat { get; set; }

        [Column(Name = "paramName")]
        public string ParamName { get; set; }
    }
}
