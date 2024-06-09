package com.vlad.sharaga.models

data class FilterDTO(
    val id: Int,
    val productType: String,
    val filterType: Int,
    val value: String,
    val filterFriendlyName: String
)