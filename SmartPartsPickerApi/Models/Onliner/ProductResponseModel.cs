// Root myDeserializedClass = JsonConvert.DeserializeObject<Root>(myJsonResponse);
using Newtonsoft.Json;

public class All
{
    [JsonProperty("term")]
    public int term { get; set; }

    [JsonProperty("label")]
    public string label { get; set; }
}

public class BYN
{
    [JsonProperty("amount")]
    public string amount { get; set; }

    [JsonProperty("currency")]
    public string currency { get; set; }
}

public class ByPartsInfo
{
    [JsonProperty("max_term")]
    public int max_term { get; set; }

    [JsonProperty("monthly_payment")]
    public MonthlyPayment monthly_payment { get; set; }

    [JsonProperty("installment")]
    public Installment installment { get; set; }
}

public class Converted
{
    [JsonProperty("BYN")]
    public BYN BYN { get; set; }
}

public class Forum
{
    [JsonProperty("topic_id")]
    public int? topic_id { get; set; }

    [JsonProperty("topic_url")]
    public string topic_url { get; set; }

    [JsonProperty("post_url")]
    public string post_url { get; set; }

    [JsonProperty("replies_count")]
    public int? replies_count { get; set; }
}

public class Images
{
    [JsonProperty("header")]
    public string header { get; set; }
}

public class Installment
{
    [JsonProperty("periods")]
    public List<int> periods { get; set; }
}

public class MaxCobrandCashback
{
    [JsonProperty("percentage")]
    public int percentage { get; set; }

    [JsonProperty("label")]
    public string label { get; set; }
}

public class MaxInstallmentTerms
{
    [JsonProperty("all")]
    public All all { get; set; }
}

public class MaxPrice
{
    [JsonProperty("amount")]
    public string amount { get; set; }

    [JsonProperty("currency")]
    public string currency { get; set; }
}

public class Meta
{
    [JsonProperty("html_url")]
    public string html_url { get; set; }
}

public class MinPrice
{
    [JsonProperty("amount")]
    public string amount { get; set; }

    [JsonProperty("currency")]
    public string currency { get; set; }
}

public class MinPricesMedian
{
    [JsonProperty("amount")]
    public string amount { get; set; }

    [JsonProperty("currency")]
    public string currency { get; set; }
}

public class MonthlyPayment
{
    [JsonProperty("amount")]
    public string amount { get; set; }

    [JsonProperty("currency")]
    public string currency { get; set; }
}

public class Offers
{
    [JsonProperty("count")]
    public int count { get; set; }
}

public class Page
{
    [JsonProperty("limit")]
    public int limit { get; set; }

    [JsonProperty("items")]
    public int items { get; set; }

    [JsonProperty("current")]
    public int current { get; set; }

    [JsonProperty("last")]
    public int last { get; set; }
}

public class PriceMax
{
    [JsonProperty("amount")]
    public string amount { get; set; }

    [JsonProperty("currency")]
    public string currency { get; set; }

    [JsonProperty("converted")]
    public Converted converted { get; set; }
}

public class PriceMin
{
    [JsonProperty("amount")]
    public string amount { get; set; }

    [JsonProperty("currency")]
    public string currency { get; set; }

    [JsonProperty("converted")]
    public Converted converted { get; set; }
}

public class Prices
{
    [JsonProperty("price_min")]
    public PriceMin price_min { get; set; }

    [JsonProperty("price_max")]
    public PriceMax price_max { get; set; }

    [JsonProperty("offers")]
    public Offers offers { get; set; }

    [JsonProperty("html_url")]
    public string html_url { get; set; }

    [JsonProperty("url")]
    public string url { get; set; }
}

public class PrimeInfo
{
    [JsonProperty("available")]
    public bool available { get; set; }
}

public class Product
{
    [JsonProperty("id")]
    public int id { get; set; }

    [JsonProperty("key")]
    public string key { get; set; }

    [JsonProperty("parent_key")]
    public string parent_key { get; set; }

    [JsonProperty("name")]
    public string name { get; set; }

    [JsonProperty("full_name")]
    public string full_name { get; set; }

    [JsonProperty("name_prefix")]
    public string name_prefix { get; set; }

    [JsonProperty("extended_name")]
    public string extended_name { get; set; }

    [JsonProperty("status")]
    public string status { get; set; }

    [JsonProperty("images")]
    public Images images { get; set; }

    [JsonProperty("description")]
    public string description { get; set; }

    [JsonProperty("description_list")]
    public List<string> description_list { get; set; }

    [JsonProperty("micro_description")]
    public string micro_description { get; set; }

    [JsonProperty("micro_description_list")]
    public List<string> micro_description_list { get; set; }

    [JsonProperty("html_url")]
    public string html_url { get; set; }

    [JsonProperty("reviews")]
    public Reviews reviews { get; set; }

    [JsonProperty("review_url")]
    public string review_url { get; set; }

    [JsonProperty("color_code")]
    public object color_code { get; set; }

    [JsonProperty("prices")]
    public Prices prices { get; set; }

    [JsonProperty("max_installment_terms")]
    public MaxInstallmentTerms max_installment_terms { get; set; }

    [JsonProperty("max_cobrand_cashback")]
    public MaxCobrandCashback max_cobrand_cashback { get; set; }

    [JsonProperty("sale")]
    public Sale sale { get; set; }

    [JsonProperty("second")]
    public Second second { get; set; }

    [JsonProperty("forum")]
    public Forum forum { get; set; }

    [JsonProperty("url")]
    public string url { get; set; }

    [JsonProperty("advertise")]
    public object advertise { get; set; }

    [JsonProperty("stickers")]
    public List<Sticker> stickers { get; set; }

    [JsonProperty("prime_info")]
    public PrimeInfo prime_info { get; set; }

    [JsonProperty("in_bookmarks")]
    public bool in_bookmarks { get; set; }

    [JsonProperty("by_parts_info")]
    public ByPartsInfo by_parts_info { get; set; }
}

public class Reviews
{
    [JsonProperty("rating")]
    public int rating { get; set; }

    [JsonProperty("count")]
    public int count { get; set; }

    [JsonProperty("html_url")]
    public string html_url { get; set; }

    [JsonProperty("url")]
    public string url { get; set; }
}

public class ProductResponseModel
{
    [JsonProperty("products")]
    public List<Product> products { get; set; }

    [JsonProperty("total_ungrouped")]
    public int total_ungrouped { get; set; }

    [JsonProperty("meta")]
    public Meta meta { get; set; }

    [JsonProperty("total")]
    public int total { get; set; }

    [JsonProperty("page")]
    public Page page { get; set; }
}

public class Sale
{
    [JsonProperty("is_on_sale")]
    public bool is_on_sale { get; set; }

    [JsonProperty("discount")]
    public int discount { get; set; }

    [JsonProperty("min_prices_median")]
    public MinPricesMedian min_prices_median { get; set; }

    [JsonProperty("subscribed")]
    public bool subscribed { get; set; }

    [JsonProperty("can_be_subscribed")]
    public bool can_be_subscribed { get; set; }
}

public class Second
{
    [JsonProperty("offers_count")]
    public int offers_count { get; set; }

    [JsonProperty("min_price")]
    public MinPrice min_price { get; set; }

    [JsonProperty("max_price")]
    public MaxPrice max_price { get; set; }
}

public class Sticker
{
    [JsonProperty("type")]
    public string type { get; set; }

    [JsonProperty("label")]
    public string label { get; set; }

    [JsonProperty("text")]
    public string text { get; set; }

    [JsonProperty("color")]
    public string color { get; set; }

    [JsonProperty("bg_color")]
    public string bg_color { get; set; }

    [JsonProperty("html_url")]
    public object html_url { get; set; }
}

