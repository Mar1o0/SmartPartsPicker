package com.smart.parts.picker.models

data class Budget(
    val products: List<Product>?,
    val totalPrice: Double?,
) {
    val isValid: Boolean
        get() = products != null && totalPrice != null
}