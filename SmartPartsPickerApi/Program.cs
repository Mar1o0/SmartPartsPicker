using SmartPartsPickerApi.Service.Internal;
using System.Text.Json.Serialization;
using System.Text.Json;
using Newtonsoft.Json.Converters;

namespace SmartPartsPickerApi
{
    public class Program
    {
        public static void Main(string[] args)
        {

            Database.Database.CreateAllDb().Wait();




            if (args.Any() && args[0] == "--need-update")
            {
                var updater = new UpdateDbService();
                updater.Reread().GetAwaiter().GetResult();
            }

            var builder = WebApplication.CreateBuilder(args);
            // Add services to the container.

            builder.Services.AddControllers()
                .AddNewtonsoftJson(options =>
                {
                    options.SerializerSettings.Converters.Add(new StringEnumConverter());
                });
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

            app.Urls.Add("http://+:8080");

            app.MapControllers();

            app.Run();
        }
    }
}
