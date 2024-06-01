package com.vlad.sharaga.models

data class ProductDescription(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrls: List<String>,
    val minPrice: Double,
    val variantsCount: Int,
    val rating: Float,
    val specs: Map<String, String>,
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
                specs = productSpecs.associate { it.type to it.value }
            )
        }

    }

}