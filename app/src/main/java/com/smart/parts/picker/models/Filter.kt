package com.smart.parts.picker.models

import com.smart.parts.picker.models.types.ProductType

data class Filter(
    val id: Int?,
    val productType: ProductType?,
    val filterType: Int?,
    val value: String?,
    val filterFriendlyName: String?,
) {
    val isValid: Boolean
        get() = id != null && productType != null && filterType != null && value != null && filterFriendlyName != null
}