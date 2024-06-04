using LinqToDB;
using SmartPartsPickerApi.Database.Tables;

namespace SmartPartsPickerApi.Database.Providers
{
    public class GameProviders
    {
        public async Task<GameTable> CreateUpdate(GameTable entity)
        {
            using var db = new DbWinbroker();
            var id = db.Games.Any() ? db.Games.Max(x => x.Id) + 1 : 1;
            entity.Id = id;
            await db.Games.InsertOrUpdateAsync(() => new GameTable()
            {
                Id = entity.Id,
                Name = entity.Name,
                ImageUrl = entity.ImageUrl,
            },
            x => new GameTable()
            {
                Id = entity.Id,
                Name = entity.Name ?? x.Name,
                ImageUrl = entity.ImageUrl ?? x.Name,
            });
            return entity;
        }

        public List<GameTable> GetAll()
        {
            using var db = new DbWinbroker();
            return db.Games.ToList();
        }

        public GameTable GetById(int id)
        {
            using var db = new DbWinbroker();
            return db.Games.FirstOrDefault(x => x.Id == id);
        }

        public GameTable GetByName(string name)
        {
            using var db = new DbWinbroker();
            return db.Games.FirstOrDefault(x => x.Name == name);
        }
    }
}
