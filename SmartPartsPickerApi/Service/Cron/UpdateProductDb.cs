using Microsoft.Extensions.Hosting;
using Newtonsoft.Json;
using Quartz;
using Quartz.Impl;
using RestSharp;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Models.Onliner;
using System.Reflection.Metadata;

namespace SmartPartsPickerApi.Service.Cron
{
    public class UpdateProductDb : BackgroundService
    {
        public UpdateProductDb()
        {
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            await UpdateProduct();
        }

        public async Task UpdateProduct()
        {
            IScheduler scheduler = await StdSchedulerFactory.GetDefaultScheduler();
            await scheduler.Start();
            var guid = Guid.NewGuid().ToString();
            IJobDetail UpTrackJob = JobBuilder.Create<UpdateProduct>()
                .WithIdentity($"StartJob{guid}", $"group{guid}")
                .StoreDurably()
                .Build();

            ITrigger triggerUpTrack = TriggerBuilder.Create()
                .WithIdentity($"trigger{guid}", $"group{guid}")
                .StartNow()
                .WithSimpleSchedule(x => x
                    .WithIntervalInSeconds(30)
                    .RepeatForever())
                .Build();
            await scheduler.ScheduleJob(UpTrackJob, triggerUpTrack);
        }
    }

    public class UpdateProduct : IJob
    {
        public async Task Execute(IJobExecutionContext context)
        {
            try
            {
                var links = new Dictionary<ProductType, string>();
                var client = new RestClient("https://catalog.api.onliner.by/");
                var request = new RestRequest("navigation/elements/2/groups");
                var response = client.ExecuteAsync(request);
                var respJson = JsonConvert.DeserializeObject<List<Category>>(response.ToString());
                var category = respJson.FirstOrDefault(x => x.Title == "Комплектующие");
                var countLinks = category.Links.Count;

                foreach (var typeId in Enum.GetValues(typeof(ProductType)))
                {
                    links.Add((ProductType)typeId, category.Links.FirstOrDefault(x => x.Id == (int)typeId).SourceUrls.CatalogSchemaProducts);
                }


            }
            catch (Exception ex)
            {
                throw;
            }
        }
    }
}
