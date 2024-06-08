package com.vlad.sharaga.models

import com.vlad.sharaga.core.adapter.recycler.fingerprints.SpecificationItem

data class ProductDescription(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrls: List<String>,
    val minPrice: Double,
    val variantsCount: Int,
    val rating: Float,
    val specs: List<SpecificationItem>,
) {

    companion object {

        fun create(
            product: Product,
            productImages: List<ProductImage>,
            productSpecs: List<ProductSpec>,
            productPrices: List<ProductPrice>
        ): ProductDescription {
            return ProductDescription(
                id = product.id,
                title = product.fullName,
                description = product.description,
                imageUrls = productImages.map { it.imageUrl },
                minPrice = productPrices.minOf { it.price },
                variantsCount = productPrices.size,
                rating = product.rating,
                specs = productSpecs.map {
                    SpecificationItem(
                        id = it.id,
                        title = it.type,
                        value = it.value
                    )
                }
            )
        }

    }

}