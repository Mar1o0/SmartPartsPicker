using Microsoft.AspNetCore.Http.Features;
using Newtonsoft.Json;
using RestSharp;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Onliner;
using SmartPartsPickerApi.Service.Filters.Factory;
using System.Text.RegularExpressions;

namespace SmartPartsPickerApi.Service.Internal
{
    public class UpdateDbService
    {
        private readonly Dictionary<string, ProductType> _filePrefixToProductType = new Dictionary<string, ProductType>
        {
            { "chassis", ProductType.CHASSIS },
            { "dram", ProductType.RAM },
            { "cpu", ProductType.CPU },
            { "motherboard", ProductType.MB },
            { "videocard", ProductType.GPU },
            { "hdd", ProductType.HDD },
            { "ssd", ProductType.SSD },
            { "powersupply", ProductType.PSU },
        };

        public async Task Reread()
        {
            try
            {
                var db = new Database.Database();
                string jsonDirectoryPath = Path.Combine(Directory.GetCurrentDirectory(), "onlinerParser");
                string[] jsonFilePaths = Directory.GetFiles(jsonDirectoryPath, "*.json");
                var existingProducts = db.Product.GetAll().ToDictionary(x => x.ApiId, x => x);
                var productsByTypes = new Dictionary<ProductType, List<Product>>();
                foreach (string jsonFilePath in jsonFilePaths)
                {
                    string fileName = Path.GetFileNameWithoutExtension(jsonFilePath);

                    string filePrefix = Regex.Match(fileName, @"^([a-zA-Z]+)").Value;


                    if (_filePrefixToProductType.TryGetValue(filePrefix, out var productType))
                    {
                        string jsonContent = File.ReadAllText(jsonFilePath);
                        var productResponse = JsonConvert.DeserializeObject<ProductResponseModel>(jsonContent);
                        if (productsByTypes.TryGetValue(productType, out var products))
                        {
                            products.AddRange(productResponse.products);
                        }
                        else
                        {
                            productsByTypes[productType] = new List<Product>();
                            productsByTypes[productType].AddRange(productResponse.products);
                        }
                    }
                    else
                    {
                        Console.WriteLine($"Неизвестный префикс файла: {filePrefix}. Пропуск файла {fileName}");
                        continue;
                    }
                }

                foreach (var productWithType in productsByTypes)
                {
                    var allFilterTabels = new Dictionary<FilterTable, IProductFilter>();

                    var productType = productWithType.Key;
                    var products = productWithType.Value;

                    var parser = new FilterParserFactory(productType);

                    var filters = parser?.ParseFilters(productWithType.Value) ?? new List<IProductFilter>();

                    db.Filter.Delete(productType);

                    foreach (var filter in filters)
                    {
                        var filterTable = new FilterTable
                        {
                            ProductType = filter.ProductType,
                            FilterType = filter.FilterType,
                            ParamName = filter.Value,
                            FilterVariat = filter.Value
                        };
                        filterTable = await db.Filter.CreateUpdate(filterTable);
                        allFilterTabels.Add(filterTable, filter);
                    }

                    foreach (var product in products)
                    {
                        try
                        {

                            if (!existingProducts.ContainsKey(product.id))
                            {
                                var newProduct = new ProductTable
                                {
                                    ApiId = product.id,
                                    Name = product.name,
                                    FullName = product.full_name,
                                    Description = product.description,
                                    Type = productType,
                                    Rating = product.reviews.rating
                                };

                                newProduct = await db.Product.CreateUpdate(newProduct);
                                existingProducts.Add(product.id, newProduct);

                                var productImage = new ProductImageTable
                                {
                                    ProductId = newProduct.Id,
                                    Href = product.images?.header
                                };
                                await db.ProductImage.CreateUpdate(productImage);

                                var client = new RestClient();
                                var request = new RestRequest(product.prices.url);
                                request.AddHeader("Cookie", "stid=59830b471eee1955b77a0832b795bc9fbfaef08c43b85052158ba004589bfcaa; fingerprint=26725356-73dd-46b1-b1aa-e2edd175d18c; oss=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjozMzY1NDg4LCJ1c2VyX3R5cGUiOiJ1c2VyIiwiZmluZ2VycHJpbnQiOiI2ODgxZWVjNDQ5NTdkN2ZlY2U5OTk4YjdiZDJmNjgzMiIsImV4cCI6MjAzMDY3MDAyOCwiaWF0IjoxNzE1MzEwMDI4fQ.FMtjECjn0R5jy0ZmJW8vUXrF4VlfzzNiWVuRiqYkibjIgX-lOFMJfl8IxnuYJNDdrU5kG8J25Vn4k7AF0RZEJg; logged_in=1; compare=%5B%5D; delivery-region-id=17030; catalog_session=hcOAtAVR7nHrJ7sDDGOSe1cZxCRpcNS9IkFOCOzz; ouid=snyBDmZlC8cNP2bbEKjoAg==; ADC_REQ_2E94AF76E7=A85C8405B9155D4A3CC968B3C0F8B6468E8ADB23F48F6DF1355FD91475FB2E56ACFA083D6A26C59E");
                                request.AddHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36");
                                request.AddHeader("sec-ch-ua", "\"Brave\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"");
                                var response = await client.GetAsync(request);
                                var responseJson = JsonConvert.DeserializeObject<ProductPositionsModel>(response.Content);

                                foreach (var item in responseJson.Positions.Primary)
                                {
                                    if (item.ProductId == product.id)
                                    {
                                        request.Resource = item.ShopFullInfoUrl;
                                        response = await client.GetAsync(request);
                                        var responseJsonNew = JsonConvert.DeserializeObject<ShopModel>(response.Content);

                                        var productPrice = new ProductPriceTable
                                        {
                                            ProductId = newProduct.Id,
                                            ShopImage = responseJsonNew.Logo,
                                            Href = responseJsonNew.HtmlUrl,
                                            Price = double.Parse(item.PositionPrice.Amount.Replace('.',',')),
                                            ShopName = responseJsonNew.Title,
                                            Currency = "BYN"
                                        };

                                        await db.ProductPrice.CreateUpdate(productPrice);
                                    }
                                }

                                foreach (var filter in allFilterTabels)
                                {
                                    if (filter.Value.IsSuitable(product))
                                    {
                                        await db.ProductFilter.CreateUpdate(new()
                                        {
                                            FilterId = filter.Key.Id,
                                            ProductId = newProduct.Id,
                                        });
                                    }
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine($"Ошибка при обработки продукта: {product.id} - {ex.Message} - {ex.StackTrace}");
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
        }
        //TODO
        private Dictionary<ProductTable, ProductPriceTable> ParsePrices(List<ProductTable> products)
        {
            throw new NotImplementedException();
        }

        private List<string> GetFilterVariants(IProductFilter filter)
        {
            return new List<string>() { filter.Value };
        }
    }
}
