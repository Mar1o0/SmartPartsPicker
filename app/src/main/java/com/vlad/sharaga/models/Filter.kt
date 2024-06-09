package com.vlad.sharaga.models

data class Filter(
    val id: Int,
    val filterName: String,
    val filterType: FilterType,
    val variants: MutableList<String>,
)