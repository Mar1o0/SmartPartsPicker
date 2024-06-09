using Microsoft.AspNetCore.Mvc;
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

        [HttpGet("product/budget/")]
        public IActionResult GetProductPacksByBudget([FromQuery] double budget)
        {
            try
            {
                /*
                 тебе дали 100000 бюджета
                 
                 100_000 * 0.075(7.5%) < cpu_price < 100_000 * 0.085 (8.5%)

                 100_000 * 0.345(34.5%) < gpu_price < 100_000 * 0.355 (35.5%)
                 
                 */

                return Ok(budget);
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
