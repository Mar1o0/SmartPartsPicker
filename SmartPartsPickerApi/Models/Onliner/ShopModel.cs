using Newtonsoft.Json;

namespace SmartPartsPickerApi.Models.Onliner
{
    // Root myDeserializedClass = JsonConvert.DeserializeObject<Root>(myJsonResponse);
    public class Address
    {
        [JsonProperty("town")]
        public Town Town;

        [JsonProperty("address")]
        public string AddressShop;

        [JsonProperty("email")]
        public string Email;

        [JsonProperty("skype")]
        public string Skype;

        [JsonProperty("viber")]
        public string Viber;

        [JsonProperty("telegram")]
        public string Telegram;

        [JsonProperty("whats_app")]
        public string WhatsApp;

        [JsonProperty("phones")]
        public List<string> Phones;

        [JsonProperty("work_time")]
        public WorkTime WorkTime;
    }

    public class Customer
    {
        [JsonProperty("title")]
        public string Title;

        [JsonProperty("unp")]
        public string Unp;

        [JsonProperty("egr")]
        public string Egr;

        [JsonProperty("address")]
        public string Address;

        [JsonProperty("registration_date")]
        public string RegistrationDate;

        [JsonProperty("registration_agency")]
        public string RegistrationAgency;

        [JsonProperty("complaint_book")]
        public string ComplaintBook;
    }

    public class Friday
    {
        [JsonProperty("from")]
        public string From;

        [JsonProperty("till")]
        public string Till;
    }

    public class Monday
    {
        [JsonProperty("from")]
        public string From;

        [JsonProperty("till")]
        public string Till;
    }

    public class OrderProcessing
    {
        [JsonProperty("schedule")]
        public Schedule Schedule;
    }

    public class PaymentConfirmationDocument
    {
        [JsonProperty("src")]
        public string Src;
    }

    public class PaymentMethods
    {
        [JsonProperty("cash")]
        public string Cash;

        [JsonProperty("card")]
        public string Card;

        [JsonProperty("bank_transfer")]
        public string BankTransfer;

        [JsonProperty("online")]
        public string Online;

        [JsonProperty("by_parts")]
        public string ByParts;
    }

    public class Reviews
    {
        [JsonProperty("rating")]
        public double Rating;

        [JsonProperty("count")]
        public int Count;

        [JsonProperty("url")]
        public string Url;
    }

    public class ShopModel
    {
        [JsonProperty("id")]
        public int Id;

        [JsonProperty("url")]
        public string Url;

        [JsonProperty("full_info_url")]
        public string FullInfoUrl;

        [JsonProperty("html_url")]
        public string HtmlUrl;

        [JsonProperty("title")]
        public string Title;

        [JsonProperty("logo")]
        public string Logo;

        [JsonProperty("is_new_shop")]
        public bool IsNewShop;

        [JsonProperty("reviews")]
        public Reviews Reviews;

        [JsonProperty("schema_phones")]
        public List<object> SchemaPhones;

        [JsonProperty("success_rating")]
        public SuccessRating SuccessRating;

        [JsonProperty("registration_date")]
        public string RegistrationDate;

        [JsonProperty("payment_methods")]
        public PaymentMethods PaymentMethods;

        [JsonProperty("payment_confirmation_documents")]
        public List<PaymentConfirmationDocument> PaymentConfirmationDocuments;

        [JsonProperty("customer")]
        public Customer Customer;

        [JsonProperty("order_processing")]
        public OrderProcessing OrderProcessing;

        [JsonProperty("addresses")]
        public List<Address> Addresses;

        [JsonProperty("warranty_contacts")]
        public WarrantyContacts WarrantyContacts;
    }

    public class Saturday
    {
        [JsonProperty("from")]
        public string From;

        [JsonProperty("till")]
        public string Till;
    }

    public class Schedule
    {
        [JsonProperty("monday")]
        public Monday Monday;

        [JsonProperty("tuesday")]
        public Tuesday Tuesday;

        [JsonProperty("wednesday")]
        public Wednesday Wednesday;

        [JsonProperty("thursday")]
        public Thursday Thursday;

        [JsonProperty("friday")]
        public Friday Friday;

        [JsonProperty("saturday")]
        public Saturday Saturday;

        [JsonProperty("sunday")]
        public Sunday Sunday;
    }

    public class SuccessRating
    {
        [JsonProperty("value")]
        public string Value;

        [JsonProperty("status")]
        public string Status;

        [JsonProperty("period")]
        public int Period;
    }

    public class Sunday
    {
        [JsonProperty("from")]
        public string From;

        [JsonProperty("till")]
        public string Till;

        [JsonProperty("holiday")]
        public string Holiday;
    }

    public class Thursday
    {
        [JsonProperty("from")]
        public string From;

        [JsonProperty("till")]
        public string Till;
    }

    public class Town
    {
        [JsonProperty("id")]
        public int Id;

        [JsonProperty("key")]
        public string Key;

        [JsonProperty("title")]
        public string Title;

        [JsonProperty("prepositional_case")]
        public string PrepositionalCase;
    }

    public class Tuesday
    {
        [JsonProperty("from")]
        public string From;

        [JsonProperty("till")]
        public string Till;
    }

    public class WarrantyContacts
    {
        [JsonProperty("phones")]
        public List<string> Phones;

        [JsonProperty("email")]
        public string Email;

        [JsonProperty("skype")]
        public string Skype;

        [JsonProperty("viber")]
        public object Viber;

        [JsonProperty("telegram")]
        public object Telegram;

        [JsonProperty("whatsApp")]
        public object WhatsApp;
    }

    public class Wednesday
    {
        [JsonProperty("from")]
        public string From;

        [JsonProperty("till")]
        public string Till;
    }

    public class WorkTime
    {
        [JsonProperty("monday")]
        public Monday Monday;

        [JsonProperty("tuesday")]
        public Tuesday Tuesday;

        [JsonProperty("wednesday")]
        public Wednesday Wednesday;

        [JsonProperty("thursday")]
        public Thursday Thursday;

        [JsonProperty("friday")]
        public Friday Friday;

        [JsonProperty("saturday")]
        public Saturday Saturday;

        [JsonProperty("sunday")]
        public Sunday Sunday;
    }


}
