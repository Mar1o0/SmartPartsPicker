package com.vlad.sharaga.network.api

import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.models.Filter
import com.vlad.sharaga.models.Product
import com.vlad.sharaga.models.ProductImage
import com.vlad.sharaga.models.ProductPrice
import com.vlad.sharaga.models.ProductSpec
import com.vlad.sharaga.models.ProductType
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchCityNames(): List<String>? {
        return apiService.fetchCityNames()
    }

    suspend fun fetchProduct(productId: ProductId): Product? {
        return apiService.fetchProduct(productId)
    }

    suspend fun fetchProducts(productType: String): List<Product>? {
        return apiService.fetchProducts(productType)
    }

    suspend fun fetchProductPrices(productId: ProductId): List<ProductPrice>? {
        return apiService.fetchProductPrices(productId)
    }

    suspend fun fetchProductImage(productId: ProductId): ProductImage? {
        return apiService.fetchProductImage(productId)
    }

    suspend fun fetchProductImages(productId: ProductId): List<ProductImage>? {
        return apiService.fetchProductImages(productId)
    }

    suspend fun fetchProductSpecs(productId: ProductId): List<ProductSpec>? {
        return apiService.fetchProductSpecs(productId)
    }

    suspend fun fetchFilters(productType: ProductType): List<Filter>? {
        return apiService.fetchFilters(productType)
    }

}
