using LinqToDB.Mapping;
using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "ProductSpec")]
    public class ProductSpecTable
    {
        [Column(Name = "id", IsPrimaryKey = true)]
        public int Id { get; set; }
        [Column(Name = "productId")]
        public int ProductId { get; set; }
        [Column(Name = "type")]
        public SpecType Type { get; set; }
        [Column(Name = "value")]
        public string Value { get; set; }
    }
}
