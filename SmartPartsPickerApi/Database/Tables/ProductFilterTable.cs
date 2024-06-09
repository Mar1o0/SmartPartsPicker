using System.ComponentModel.DataAnnotations.Schema;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table("ProductFilter")]
    public class ProductFilterTable
    {
        [Column("filter_id")]
        public int FilterId { get; set; }
        [Column("product_id")]
        public int ProductId { get; set; }
    }
}
