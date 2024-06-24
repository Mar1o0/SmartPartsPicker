package com.smart.parts.picker.models

import android.os.Parcelable
import com.smart.parts.picker.models.types.ProductType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductSpec(
    val id: Int?,
    val productType: ProductType?,
    val filterType: Int?,
    val value: String?,
    val filterFriendlyName: String?,
): Parcelable {
    val isValid: Boolean
        get() = id != null && productType != null && filterType != null && value != null && filterFriendlyName != null
}