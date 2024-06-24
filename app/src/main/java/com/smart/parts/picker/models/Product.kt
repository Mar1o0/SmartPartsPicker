package com.smart.parts.picker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int?,
    val type: String?,
    val price: List<ProductPrice>?,
    val specs: List<ProductSpec>?,
    val image: String?,
    val apiId: Int?,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val rating: Int?,
): Parcelable {
    val isValid: Boolean
        get() = id != null && type != null && price != null && image != null && apiId != null && name != null && fullName != null && description != null && rating != null
}