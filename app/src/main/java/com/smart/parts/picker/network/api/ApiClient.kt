package com.smart.parts.picker.network.api

import com.smart.parts.picker.models.Budget
import com.smart.parts.picker.models.types.ProductId
import com.smart.parts.picker.models.Filter
import com.smart.parts.picker.models.Product
import com.smart.parts.picker.models.ProductSpec
import com.smart.parts.picker.models.types.ProductType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun fetchCityNames(): List<String>? = withContext(Dispatchers.IO) {
        kotlin.runCatching {
//            apiService.fetchCityNames()
            listOf(
                "Гродно", "Минск", "Могилев", "Витебск", "Брест", "Гомель"
            )
        }.getOrNull()
    }

    suspend fun fetchProducts(
        productType: ProductType,
        priceMin: Double,
        priceMax: Double,
        filters: String,
        page: Int,
        perPage: Int,
    ): List<Product>? = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            apiService.fetchProducts(productType, priceMin, priceMax, filters, page, perPage)
        }.getOrNull()
    }

    suspend fun fetchProduct(
        productId: ProductId,
    ): Product? = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            apiService.fetchProduct(productId)
        }.getOrNull()
    }

    suspend fun fetchBudgetAssemblies(
        budget: Double,
    ): List<Budget>? = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            apiService.fetchBudgetAssemblies(budget)
        }.getOrNull()
    }

    suspend fun fetchFilters(
        productType: ProductType,
    ): List<Filter>? = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            apiService.fetchFilters(productType)
        }.getOrNull()
    }

    suspend fun searchProducts(
        productType: ProductType,
        query: String,
    ): List<Product>? = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            apiService.searchProducts(productType, query)
        }.getOrNull()
    }
}
