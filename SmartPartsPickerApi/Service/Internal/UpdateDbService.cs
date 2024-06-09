using Newtonsoft.Json;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Service.Filters;
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

        public async Task Reread() {
            try
            {
                var db = new Database.Database();
                string jsonDirectoryPath = Path.Combine(Directory.GetCurrentDirectory(), "onlinerParser");
                string[] jsonFilePaths = Directory.GetFiles(jsonDirectoryPath, "*.json");

                foreach (string jsonFilePath in jsonFilePaths)
                {
                    string fileName = Path.GetFileNameWithoutExtension(jsonFilePath);

                    string filePrefix = Regex.Match(fileName, @"^([a-zA-Z]+)").Value;

                    if (!_filePrefixToProductType.ContainsKey(filePrefix))
                    {
                        Console.WriteLine($"Неизвестный префикс файла: {filePrefix}. Пропуск файла {fileName}");
                        continue;
                    }

                    ProductType productType = _filePrefixToProductType[filePrefix];

                    string jsonContent = File.ReadAllText(jsonFilePath);
                    var productResponse = JsonConvert.DeserializeObject<ProductResponseModel>(jsonContent);
                    if (productResponse != null && productResponse.products != null)
                    {
                        var parser = GetProductFilterParser(productType);
                        var filters = parser?.ParseFilters(productResponse.products) ?? new List<IProductFilter>();

                        foreach (var product in productResponse.products)
                        {
                            try
                            {
                                var existingProduct = db.Product.GetAll()
                                    .FirstOrDefault(x => x.ApiId == product.id.ToString() && x.Type == productType);

                                if (existingProduct == null)
                                {
                                    var newProduct = new ProductTable
                                    {
                                        ApiId = product.id.ToString(),
                                        Name = product.name,
                                        FullName = product.full_name,
                                        Description = product.description,
                                        Type = productType
                                    };

                                    var productDb = await db.Product.CreateUpdate(newProduct);

                                    var productImage = new ProductImageTable
                                    {
                                        ProductId = productDb.Id,
                                        Href = product.images?.header
                                    };
                                    await db.ProductImage.CreateUpdate(productImage);

                                    if (product.prices != null)
                                    {
                                        var productPrice = new ProductPriceTable
                                        {
                                            ProductId = productDb.Id,
                                            ShopName = "onliner.by",
                                            Href = product.prices.url,
                                            Price = double.Parse(product.prices.price_min.amount),
                                            Currency = product.prices.price_min.currency
                                        };
                                        await db.ProductPrice.CreateUpdate(productPrice);
                                    }

                                    //foreach (var description in product.description_list)
                                    //{
                                    //    //TODO: ИСПРАВИТЬ, ПЕРЕДЕЛАТЬ, ИЗМЕНИТЬ
                                    //    var productSpecs = ParseProductDescription(description, productType);

                                    //    foreach (var spec in productSpecs)
                                    //    {
                                    //        var specTable = new ProductSpecTable
                                    //        {
                                    //            Type = spec.Key,
                                    //            Value = spec.Value,
                                    //            ProductId = productDb.Id,
                                    //        };

                                    //        await db.ProductSpec.CreateUpdate(specTable);
                                    //    }
                                    //}
                                }
                            }
                            catch (Exception ex)
                            {
                                Console.WriteLine($"Ошибка при обработки продукта: {product.id} - {ex.Message}");
                            }
                        }

                        foreach (var filter in filters)
                        {
                            var filterTable = new FilterTable
                            {
                                ProductType = filter.ProductType,
                                FilterType = filter.FilterType,
                            };

                            await db.Filter.CreateUpdate(filterTable);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
        }

        private List<string> GetFilterVariants(IProductFilter filter)
        {
            return new List<string>() { filter.Value };
        }

        private IProductFilterParser GetProductFilterParser(ProductType productType)
        {
            switch (productType)
            {
                case ProductType.GPU:
                    return new VideoCardFilterParser();
                case ProductType.CHASSIS:
                    return new ChassisFilterParser();
                case ProductType.CPU:
                    return new CpuFilterParser();
                case ProductType.RAM:
                    return new DramFilterParser();
                case ProductType.HDD:
                    return new HddFilterParser();
                case ProductType.MB:
                    return new MotherBoardFilterParser();
                case ProductType.PSU:
                    return new PowerSupplyFilterParser();
                case ProductType.SSD:
                    return null;
                default:
                    return null; 
            }
        }
    }
}
