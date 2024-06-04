using LinqToDB;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;

namespace SmartPartsPickerApi.Database.Providers
{
    public class GameSpecProviders
    {
        public async Task<GameSpecTable> CreateUpdate(GameSpecTable entity)
        {
            using var db = new DbWinbroker();
            var id = db.GameSpecs.Any() ? db.GameSpecs.Max(x => x.Id) + 1 : 1;
            entity.Id = id;
            await db.GameSpecs.InsertOrUpdateAsync(() => new GameSpecTable()
            {
                Id = entity.Id,
                GameId = entity.GameId,
                Type = entity.Type,
                Value = entity.Value,
                IsEnchanced = entity.IsEnchanced,
            },
            x => new GameSpecTable()
            {
                Id = entity.Id,
                GameId = entity.GameId == x.GameId ? x.GameId : entity.GameId,
                Type = entity.Type == x.Type ? x.Type : entity.Type,
                Value = entity.Value == x.Value ? x.Value : entity.Value,
                IsEnchanced = entity.IsEnchanced == x.IsEnchanced ? x.IsEnchanced : entity.IsEnchanced,
            });
            return entity;
        }

        public List<GameSpecTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.GameSpecs.ToList();
        }

        public GameSpecTable GetById(int id)
        {
            using var db = new DbWinbroker();
            return db.GameSpecs.FirstOrDefault(x => x.Id == id);
        }

        public GameSpecTable GetByGameId(int gameId)
        {
            using var db = new DbWinbroker();
            return db.GameSpecs.FirstOrDefault(x => x.GameId == gameId);
        }

        public List<GameSpecTable> GetByType(SpecType type)
        {
            using var db = new DbWinbroker();
            return db.GameSpecs.Where(x => x.Type == type).ToList();
        }
        public List<GameSpecTable> GetByEnchanced(bool isEnchanced)
        {
            using var db = new DbWinbroker();
            return db.GameSpecs.Where(x => x.IsEnchanced == isEnchanced).ToList();
        }
    }
}
