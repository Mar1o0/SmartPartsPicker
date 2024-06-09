using SmartPartsPickerApi.Service.Internal;
using System.Text.Json.Serialization;
using System.Text.Json;

namespace SmartPartsPickerApi
{
    public class Program
    {
        public static void Main(string[] args)
        {

            Database.Database.CreateAllDb().Wait();

            #region MyRegion
            //var parser = new VideoCardFilterParser();

            //var filters = parser.ParseFilters(null);

            //foreach (var item in filters)
            //{
            //    switch (item.ProductType)
            //    {
            //        case Enums.ProductType.CPU:
            //            var cpu = (CpuFilter)item;
            //            var nameCpu = cpu.FilterFriendlyName;
            //            break;
            //        case Enums.ProductType.GPU:
            //            var gpu = (VideoCardFilter)item;
            //            var nameGpu = gpu.FilterFriendlyName;
            //            break;
            //        case Enums.ProductType.MB:
            //            var mb = (MotherBoardFilter)item;
            //            var nameMb = mb.FilterFriendlyName;
            //            break;
            //        case Enums.ProductType.RAM:
            //            var ram = (DramFilter)item;
            //            var nameRam = ram.FilterFriendlyName;
            //            break;
            //        case Enums.ProductType.PSU:
            //            var psu = (PowerSupplyFilter)item;
            //            var namePsu = psu.FilterFriendlyName;
            //            break;
            //        case Enums.ProductType.CHASSIS:
            //            var chassis = (ChassisFilter)item;
            //            var nameChassis = chassis.FilterFriendlyName;
            //            break;
            //        case Enums.ProductType.HDD:
            //            var hdd = (HddFilter)item;
            //            var nameHdd = hdd.FilterFriendlyName;
            //            break;
            //        case Enums.ProductType.SSD:
            //            break;
            //    }
            //} 
            #endregion

            args = new string[] { "--need-update" };

            if (args.Any() && args[0] == "--need-update")
            {
                var updater = new UpdateDbService();
                updater.Reread().GetAwaiter().GetResult();
            }

            var builder = WebApplication.CreateBuilder(args);
            // Add services to the container.

            builder.Services.AddControllers().AddNewtonsoftJson();
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
