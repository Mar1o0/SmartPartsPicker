using LinqToDB.Mapping;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "ProductPrice")]
    public class ProductPriceTable
    {
        [Column(Name = "id", IsPrimaryKey = true)]
        public int Id { get; set; }
        [Column(Name = "productId")]
        public int ProductId { get; set; }
        [Column(Name = "shopName")]
        public string ShopName { get; set; }
        [Column(Name = "href")]
        public string Href { get; set; }
        [Column(Name = "price")]
        public double Price { get; set; }
        [Column(Name = "currency")]
        public string Currency { get; set; } = "BYN";
    }
}
