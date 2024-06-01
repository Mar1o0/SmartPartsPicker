package com.vlad.sharaga.models

data class ProductPrice(
    val id: Int,
    val productId: Int,
    val shopName: String,
    val url: String,
    val imageUrl: String,
    val price: Double,
    val currency: String,
)
