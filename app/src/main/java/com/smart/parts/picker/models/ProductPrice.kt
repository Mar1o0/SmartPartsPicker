package com.smart.parts.picker.models

import android.os.Parcelable
import com.smart.parts.picker.models.types.ProductId
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductPrice(
    val id: Int?,
    val productId: ProductId?,
    val shopName: String?,
    val href: String?,
    val shopImage: String?,
    val price: Double?,
    val currency: String?,
): Parcelable {
    val isValid: Boolean
        get() = id != null && productId != null && shopName != null && href != null && shopImage != null && price != null && currency != null
}
