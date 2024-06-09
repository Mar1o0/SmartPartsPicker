using LinqToDB.Mapping;
using Newtonsoft.Json;
using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "Product")]
    public class ProductTable
    {
        [Column(Name = "id", IsPrimaryKey = true)]
        public int Id { get; set; }
        [Column(Name = "type")]
        public ProductType Type { get; set; }
        [Column(Name = "apiId")]
        public int ApiId { get; set; }
        [Column(Name = "name")]
        public string Name { get; set; }
        [Column(Name = "fullname")]
        public string FullName { get; set; }
        [Column(Name = "description")]
        public string Description { get; set; }
        [Column(Name = "reviews")]
        public int Reviews { get; set; }
    }
}
