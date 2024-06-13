using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SmartPartsPickerApi.Database.Tables;
using SmartPartsPickerApi.Database.Views;
using SmartPartsPickerApi.Enums;
using SmartPartsPickerApi.Interfaces;
using SmartPartsPickerApi.Models.Filters;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace SmartPartsPickerApi.Controllers
{
    [Route("api")]
    [ApiController]
    public class ProductController : ControllerBase
    {
        Database.Database _db = new Database.Database();

        [HttpGet("products/{productType}")]
        public IActionResult GetProductsByType(ProductType productType,
                                               [FromQuery] double priceMin = 0,
                                               [FromQuery] double priceMax = double.MaxValue,
                                               [FromQuery] string filters = null,
                                               [FromQuery] int page = 1,
                                               [FromQuery] int per_page = 20)
        {
            try
            {
                var response = _db.Product.GetAllProductByFilters(productType, priceMin, priceMax, filters, page, per_page);

                return Ok(response);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("product/{productId:int}")]
        public IActionResult GetProduct(int productId)
        {
            try
            {
                var products = _db.Product.GetProductById(productId);

                return Ok(products);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
        
        [HttpGet("product/search/{productType}")]
        public IActionResult GetProductBySearch(ProductType productType, [FromQuery] string search)
        {
            try
            {
                var products = _db.Product.GetProductBySearch(search, productType);

                return Ok(products);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("product/budget/")]
        public IActionResult GetProductPacksByBudget([FromQuery] double budget)
        {
            try
            {
                Dictionary<ProductType, double> budgetDistribution = new()
                    {
                        // Бюджет 2400
                        { ProductType.CPU, 0.20 },      // Мин: 456     Макс: 504
                        { ProductType.MB, 0.10 },       // Мин: 228     Макс: 252
                        { ProductType.CHASSIS, 0.06 },  // Мин: 136,8   Макс: 151,2
                        { ProductType.PSU, 0.08 },      // Мин: 182,4   Макс: 201,6
                        { ProductType.GPU, 0.41 },      // Мин: 934,8   Макс: 1033,2
                        { ProductType.RAM, 0.09 },      // Мин: 205,2   Макс: 226,8
                        { ProductType.HDD, 0.06 },      // Мин: 136,8   Макс: 151,2
                        //{ ProductType.SSD, 0.03 }  // TODO: Надо сделать SSD как часть от "Накопителей"
                    };
                var productPacks = new List<object>(); // Используем object для гибкости

                // Получаем списки комплектующих для каждого типа
                var componentsByType = budgetDistribution.ToDictionary(
                    kv => kv.Key,
                    kv => _db.Product.GetAllProductByFilters(kv.Key, budget * kv.Value * 0.95, budget * kv.Value * 1.05/*, per_page: 1000*/)
                );

                // Проверка на наличие комплектующих для каждого типа
                if (componentsByType.Values.Any(list => list.Count == 0))
                {
                    return NotFound("Не удалось найти комплектующие для всех категорий в заданном бюджете.");
                }

                // Генерируем комбинации (наборы) комплектующих
                var productTypeValues = budgetDistribution.Keys.ToList();


                // Создаем начальную комбинацию, в которой каждый тип комплектующего представлен хотя бы одним продуктом
                for (int i = 0; i < componentsByType.Min(x => x.Value.Count); i++)
                {
                    var productPack = new List<FilteredProductView>();

                    foreach (var productType in productTypeValues)
                    {
                        productPack.Add(componentsByType[productType][i]);
                    }

                    productPacks.Add(new
                    {
                        products = productPack,
                        totalPrice = productPack.Sum(p => p.Price.Min(price => price.Price))
                    }); 
                }

                return Ok(productPacks);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("product/filter/{productType}")]
        public IActionResult GetFiltersByProductType(ProductType productType)
        {
            try
            {
                var filters = _db.Filter.GetByProductType(productType);
                switch (productType)
                {
                    case ProductType.CPU:
                        return Ok(filters.Select(x=> new CpuFilter(x)).ToList());
                    case ProductType.GPU:
                        return Ok(filters.Select(x=> new VideoCardFilter(x)).ToList());
                    case ProductType.PSU:
                        return Ok(filters.Select(x=> new PowerSupplyFilter(x)).ToList());
                    case ProductType.RAM:
                        return Ok(filters.Select(x=> new DramFilter(x)).ToList()); 
                    case ProductType.CHASSIS:
                        return Ok(filters.Select(x=> new ChassisFilter(x)).ToList());
                    case ProductType.MB:
                        return Ok(filters.Select(x=> new MotherBoardFilter(x)).ToList());
                    case ProductType.HDD:
                        return Ok(filters.Select(x=> new HddFilter(x)).ToList());
                    //case ProductType.SSD:
                    //    return Ok(filters.Select(x=> new SsdFiter(x)).ToList());
                    default:
                        return Ok(filters);
                }
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
