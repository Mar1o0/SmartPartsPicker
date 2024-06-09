using Newtonsoft.Json;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
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
                        if(productsByTypes.TryGetValue(productType, out var products))
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

                foreach (var productWithType in productsByTypes.Where(x=>x.Key == ProductType.CPU)/*ALARM THAT WAS JUST FOR TEST*/)
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
                                    Type = productType
                                };

                                newProduct = await db.Product.CreateUpdate(newProduct);
                                existingProducts.Add(product.id, newProduct);


                                var productImage = new ProductImageTable
                                {
                                    ProductId = newProduct.Id,
                                    Href = product.images?.header
                                };
                                await db.ProductImage.CreateUpdate(productImage);


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
                            Console.WriteLine($"Ошибка при обработки продукта: {product.id} - {ex.Message}");
                        }

                                Variant = filter.Value
                            };

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
