using Newtonsoft.Json;

namespace SmartPartsPickerApi.Models.Onliner
{
    // Root myDeserializedClass = JsonConvert.DeserializeObject<Root>(myJsonResponse);
    public class AvailablePaymentTypes
    {
        [JsonProperty("online")]
        public bool Online;

        [JsonProperty("halva")]
        public bool Halva;

        [JsonProperty("by_parts")]
        public bool ByParts;

        [JsonProperty("cashless")]
        public bool Cashless;

        [JsonProperty("cash")]
        public bool? Cash;

        [JsonProperty("terminal")]
        public bool? Terminal;
    }

    public class BYN
    {
        [JsonProperty("amount")]
        public string Amount;

        [JsonProperty("currency")]
        public string Currency;
    }

    public class ByPartsInfo
    {
        [JsonProperty("max_term")]
        public int MaxTerm;

        [JsonProperty("monthly_payment")]
        public MonthlyPayment MonthlyPayment;

        [JsonProperty("installment")]
        public Installment Installment;
    }

    public class Cashback
    {
        [JsonProperty("amount")]
        public string Amount;

        [JsonProperty("currency")]
        public string Currency;
    }

    public class CobrandInfo
    {
        [JsonProperty("cashback")]
        public Cashback Cashback;

        [JsonProperty("cashback_percentage")]
        public int CashbackPercentage;

        [JsonProperty("is_high_cashback")]
        public bool IsHighCashback;

        [JsonProperty("is_max_cashback")]
        public bool IsMaxCashback;

        [JsonProperty("is_limited_cashback")]
        public bool IsLimitedCashback;
    }

    public class Converted
    {
        [JsonProperty("BYN")]
        public BYN BYN;
    }

    public class Coordinates
    {
        [JsonProperty("lat")]
        public double Lat;

        [JsonProperty("long")]
        public double Long;
    }

    public class Delivery
    {
        [JsonProperty("courier")]
        public object Courier;

        [JsonProperty("pickup_point")]
        public PickupPoint PickupPoint;
    }

    public class HalvaPrice
    {
        [JsonProperty("amount")]
        public string Amount;

        [JsonProperty("currency")]
        public string Currency;

        [JsonProperty("term")]
        public int Term;

        [JsonProperty("amount_per_month")]
        public string AmountPerMonth;
    }

    public class Installment
    {
        [JsonProperty("periods")]
        public List<int> Periods;
    }

    public class InstallmentPrices
    {
        [JsonProperty("halva_price")]
        public HalvaPrice HalvaPrice;
    }

    public class Item
    {
        [JsonProperty("id")]
        public int Id;

        [JsonProperty("payment_types")]
        public PaymentTypes PaymentTypes;

        [JsonProperty("coordinates")]
        public Coordinates Coordinates;

        [JsonProperty("price")]
        public Price Price;

        [JsonProperty("time")]
        public int Time;
    }

    public class MinPrice
    {
        [JsonProperty("amount")]
        public string Amount;

        [JsonProperty("currency")]
        public string Currency;
    }

    public class MonthlyPayment
    {
        [JsonProperty("amount")]
        public string Amount;

        [JsonProperty("currency")]
        public string Currency;
    }

    public class OdStatus
    {
        [JsonProperty("title")]
        public string Title;

        [JsonProperty("description")]
        public string Description;

        [JsonProperty("icon")]
        public string Icon;
    }

    public class PaymentTypes
    {
        [JsonProperty("online")]
        public bool Online;

        [JsonProperty("halva")]
        public bool Halva;

        [JsonProperty("by_parts")]
        public bool ByParts;

        [JsonProperty("cash")]
        public bool Cash;

        [JsonProperty("terminal")]
        public bool Terminal;

        [JsonProperty("cashless")]
        public bool Cashless;
    }

    public class PickupPoint
    {
        [JsonProperty("min_price")]
        public MinPrice MinPrice;

        [JsonProperty("time")]
        public int Time;

        [JsonProperty("items")]
        public List<Item> Items;

        [JsonProperty("online_payment")]
        public bool OnlinePayment;

        [JsonProperty("halva_payment")]
        public bool HalvaPayment;

        [JsonProperty("by_parts_payment")]
        public bool ByPartsPayment;

        [JsonProperty("cash_payment")]
        public bool CashPayment;

        [JsonProperty("terminal_payment")]
        public bool TerminalPayment;

        [JsonProperty("cashless_payment")]
        public bool CashlessPayment;
    }

    public class PositionPrice
    {
        [JsonProperty("amount")]
        public string Amount;

        [JsonProperty("currency")]
        public string Currency;

        [JsonProperty("converted")]
        public Converted Converted;
    }

    public class Positions
    {
        [JsonProperty("primary")]
        public List<Primary> Primary;

        [JsonProperty("extra")]
        public List<object> Extra;

        [JsonProperty("original_positions_count")]
        public int OriginalPositionsCount;
    }

    public class Price
    {
        [JsonProperty("amount")]
        public string Amount;

        [JsonProperty("currency")]
        public string Currency;
    }

    public class Primary
    {
        [JsonProperty("id")]
        public string Id;

        [JsonProperty("product_id")]
        public int ProductId;

        [JsonProperty("article")]
        public string Article;

        [JsonProperty("manufacturer_id")]
        public int ManufacturerId;

        [JsonProperty("shop_id")]
        public int ShopId;

        [JsonProperty("schema_id")]
        public int SchemaId;

        [JsonProperty("position_price")]
        public PositionPrice PositionPrice;

        [JsonProperty("available_payment_types")]
        public AvailablePaymentTypes AvailablePaymentTypes;

        [JsonProperty("installment_prices")]
        public InstallmentPrices InstallmentPrices;

        [JsonProperty("cobrand_info")]
        public CobrandInfo CobrandInfo;

        [JsonProperty("promotions")]
        public Promotions Promotions;

        [JsonProperty("is_cashless")]
        public bool IsCashless;

        [JsonProperty("warranty")]
        public int Warranty;

        [JsonProperty("product_life_time")]
        public object ProductLifeTime;

        [JsonProperty("comment")]
        public string Comment;

        [JsonProperty("producer")]
        public string Producer;

        [JsonProperty("importer")]
        public string Importer;

        [JsonProperty("service_centers")]
        public string ServiceCenters;

        [JsonProperty("date_update")]
        public DateTime DateUpdate;

        [JsonProperty("delivery")]
        public Delivery Delivery;

        [JsonProperty("stock_status")]
        public StockStatus StockStatus;

        [JsonProperty("by_parts_info")]
        public ByPartsInfo ByPartsInfo;

        [JsonProperty("prime_info")]
        public PrimeInfo PrimeInfo;

        [JsonProperty("shop_url")]
        public string ShopUrl;

        [JsonProperty("shop_full_info_url")]
        public string ShopFullInfoUrl;

        [JsonProperty("product_url")]
        public string ProductUrl;

        [JsonProperty("schema_url")]
        public string SchemaUrl;

        [JsonProperty("od_status")]
        public OdStatus OdStatus;

        [JsonProperty("price_order_priority")]
        public object PriceOrderPriority;

        [JsonProperty("is_highlight_promotion_winner")]
        public bool IsHighlightPromotionWinner;
    }

    public class PrimeInfo
    {
        [JsonProperty("quantity_left")]
        public int QuantityLeft;

        [JsonProperty("payment_types")]
        public PaymentTypes PaymentTypes;

        [JsonProperty("delivery")]
        public object Delivery;
    }

    public class Promotions
    {
        [JsonProperty("is_active_mastercard_free_delivery")]
        public bool IsActiveMastercardFreeDelivery;

        [JsonProperty("is_mastercard_free_delivery_available")]
        public bool IsMastercardFreeDeliveryAvailable;
    }

    public class ProductPositionsModel
    {
        [JsonProperty("positions")]
        public Positions Positions;
        [JsonProperty("shops")]
        public object Shops;
    }

    public class StockStatus
    {
        [JsonProperty("code")]
        public string Code;

        [JsonProperty("text")]
        public string Text;

        [JsonProperty("comment")]
        public string Comment;

        [JsonProperty("importers")]
        public List<object> Importers;
    }


}
