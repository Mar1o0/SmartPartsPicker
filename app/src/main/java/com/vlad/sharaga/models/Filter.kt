package com.vlad.sharaga.models

data class Filter(
    val id: Int,
    val productType: ProductType,
    val parameterName: String,
)