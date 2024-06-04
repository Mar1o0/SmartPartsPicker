using Newtonsoft.Json;

namespace SmartPartsPickerApi.Models.Onliner
{
    public class Link
    {
        [JsonProperty("id")]
        public int Id;

        [JsonProperty("title")]
        public string Title;

        [JsonProperty("is_new")]
        public bool IsNew;

        [JsonProperty("preview")]
        public string Preview;

        [JsonProperty("count")]
        public int Count;

        [JsonProperty("price_min")]
        public PriceMin PriceMin;

        [JsonProperty("type")]
        public string Type;

        [JsonProperty("url")]
        public string Url;

        [JsonProperty("source_urls")]
        public SourceUrls SourceUrls;
    }

    public class PriceMin
    {
        [JsonProperty("amount")]
        public string Amount;

        [JsonProperty("currency")]
        public string Currency;
    }

    public class Category
    {
        [JsonProperty("id")]
        public int Id;

        [JsonProperty("title")]
        public string Title;

        [JsonProperty("links")]
        public List<Link> Links;
    }

    public class SourceUrls
    {
        [JsonProperty("catalog.schema")]
        public string CatalogSchema;

        [JsonProperty("catalog.schema.products")]
        public string CatalogSchemaProducts;

        [JsonProperty("catalog.schema.facets")]
        public string CatalogSchemaFacets;

        [JsonProperty("catalog.schema.second.offers")]
        public string CatalogSchemaSecondOffers;
    }
}
