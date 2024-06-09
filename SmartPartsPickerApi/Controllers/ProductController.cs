using Microsoft.AspNetCore.Mvc;
using SmartPartsPickerApi.Enums;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace SmartPartsPickerApi.Controllers
{
    [Route("api")]
    [ApiController]
    public class ProductController : ControllerBase
    {
        Database.Database _db = new Database.Database();

        [HttpGet("products/{productType:regex(CPU|GPU|MB|RAM|SSD|HDD|PSU|CHASSIS)}")]
        public IActionResult GetProductsByType(ProductType productType)
        {
            try
            {
                var product = _db.Product.GetAllProductByType(productType);

                return Ok(product);
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

        [HttpGet("product/budget/{budget:double}")]
        public IActionResult GetProductPacksByBudget(double budget)
        {
            try
            {
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("product/spec/{productSpecType}")]
        public IActionResult GetProductListBySpecType(SpecType productSpecType) // TODO: заменить на нормальные спецификации(т.е. enum) 
        {
            try
            {
                var product = _db.ProductSpec.GetAllBySpecType(productSpecType);

                return Ok(product);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("product/filter/{productType:regex(CPU|GPU|MB|RAM|SSD|HDD|PSU|CHASSIS)}")]
        public IActionResult GetFiltersByProductType(ProductType productType)
        {
            try
            {
                var filters = _db.Filter.GetByProductType(productType);

                return Ok(filters);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
