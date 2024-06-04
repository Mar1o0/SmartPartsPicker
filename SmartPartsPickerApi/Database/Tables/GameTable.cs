using LinqToDB.Mapping;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "Game")]
    public class GameTable
    {
        [Column(Name = "id", IsPrimaryKey = true)]
        public int Id { get; set; }
        [Column(Name = "name")]
        public string Name { get; set; }
        [Column(Name = "imageUrl")]
        public string ImageUrl { get; set; }
    }
}
