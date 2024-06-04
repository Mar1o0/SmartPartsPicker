using SmartPartsPickerApi.Enums;
using LinqToDB.Mapping;

namespace SmartPartsPickerApi.Database.Tables
{
    [Table(Name = "GameSpec")]
    public class GameSpecTable
    {
        [Column(Name = "id", IsPrimaryKey = true)]
        public int Id { get; set; }
        [Column(Name = "gameId")]
        public int GameId { get; set; }
        [Column(Name = "type")]
        public SpecType Type { get; set; }
        [Column(Name = "value")]
        public int Value { get; set; }
        [Column(Name = "isEnchanced")]
        public bool IsEnchanced { get; set; }
    }
}
