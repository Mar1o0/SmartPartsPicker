using LinqToDB.Mapping;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "FilterVariant")]
    public class FilterVariantTable
    {
        [Column(Name = "id", IsPrimaryKey = true)]
        public int Id { get; set; }
        [Column(Name = "filterId")]
        public int FilterId { get; set; }
        [Column(Name = "variant")]
        public string Variant { get; set; }
        [Column(Name = "friendlyVariantName")]
        public string FriendlyVariantName { get; set; }
    }
}
