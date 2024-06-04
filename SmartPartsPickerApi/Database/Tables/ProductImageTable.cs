using LinqToDB.Mapping;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "ProductImage")]
    public class ProductImageTable
    {
        [Column(Name = "id", IsPrimaryKey = true)]
        public int Id { get; set; }
        [Column(Name = "productId")]
        public int ProductId { get; set; }
        [Column(Name = "href")]
        public string Href { get; set; }
    }
}
