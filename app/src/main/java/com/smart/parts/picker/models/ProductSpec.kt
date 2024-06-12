package com.smart.parts.picker.models

data class ProductSpec(
    val id: Int?,
    val productId: Int?,
    val type: String?,
    val value: String?,
) {
    val isValid: Boolean
        get() = id != null && productId != null && type != null && value != null
}