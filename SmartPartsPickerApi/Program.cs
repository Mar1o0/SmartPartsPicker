
using SmartPartsPickerApi.Models.Filters;
using SmartPartsPickerApi.Service.Cron;
using SmartPartsPickerApi.Service.Filters;
using SmartPartsPickerApi.Service.Internal;

namespace SmartPartsPickerApi
{
    public class Program
    {
        public static void Main(string[] args)
        {

            Database.Database.CreateAllDb().Wait();

            var parser = new VideoCardFilterParser();

            var filters = parser.ParseFilters(null);

            foreach (var item in filters)
            {
                switch (item.ProductType)
                {
                    case Enums.ProductType.CPU:
                        break;
                    case Enums.ProductType.GPU:
                        var gpu = (VideoCardFilter)item;
                        var name = gpu.FilterFriendlyName;
                        break;
                    case Enums.ProductType.MB:
                        break;
                    case Enums.ProductType.RAM:
                        break;
                    case Enums.ProductType.SSD:
                        break;
                    case Enums.ProductType.HDD:
                        break;
                    case Enums.ProductType.PSU:
                        break;
                    case Enums.ProductType.CASE:
                        break;
                        break;
                }
            }

            if (args.Any() && args[0] == "--need-update")
            {
                var updater = new UpdateDbService();
                updater.Reread();
            }

            var builder = WebApplication.CreateBuilder(args);
            // Add services to the container.
            builder.Services.AddSingleton<UpdateProductDb>();

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            var app = builder.Build();
            
            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseHttpsRedirection();

            app.UseAuthorization();


            app.MapControllers();

            app.Run();
        }
    }
}
