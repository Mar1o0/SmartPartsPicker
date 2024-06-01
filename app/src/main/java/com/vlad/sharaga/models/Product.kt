package com.vlad.sharaga.models

data class Product(
    val id: Int,
    val type: String,
    val apiId: String,
    val name: String,
    val fullName: String,
    val description: String,
    val rating: Float,
)