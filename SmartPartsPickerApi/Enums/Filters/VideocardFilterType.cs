namespace SmartPartsPickerApi.Enums.Filters
{
    public enum VideoCardFilterType
    {
        VRAM,
        Manufacturer,
        Width,
        RT,
        Fans
    }
}
/*
 Table Filters{
    id int pk
    product_type int
    filter_type int
}
Table FilterVariants
{
    filter_id int
    variant varchar(255)
}
Ref: FilterVariants.filter_id > Filters.id
 */