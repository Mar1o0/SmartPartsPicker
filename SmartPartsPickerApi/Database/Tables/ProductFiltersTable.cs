using LinqToDB.Mapping;
using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "ProductFilters")]
    public class ProductFiltersTable
    {
        [Column(Name = "filterId")]
        public int FilterId { get; set; }
        [Column(Name = "productId")]
        public int ProductId { get; set; }
    }
}
