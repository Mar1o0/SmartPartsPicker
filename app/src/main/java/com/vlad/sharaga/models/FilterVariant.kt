package com.vlad.sharaga.models

data class FilterVariant(
    val id: Int,
    val filterId: Int,
    val variant: String,
    val friendlyVariantName: String
)