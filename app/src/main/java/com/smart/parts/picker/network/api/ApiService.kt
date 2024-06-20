package com.smart.parts.picker.network.api

import com.smart.parts.picker.models.Budget
import com.smart.parts.picker.models.types.ProductId
import com.smart.parts.picker.models.Filter
import com.smart.parts.picker.models.Product
import com.smart.parts.picker.models.ProductSpec
import com.smart.parts.picker.models.types.ProductType
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/cities/")
    suspend fun fetchCityNames(): List<String>?

    @GET("api/products/{productType}")
    suspend fun fetchProducts(
        @Path("productType") productType: ProductType,
        @Query("priceMin") priceMin: Double,
        @Query("priceMax") priceMax: Double,
        @Query("filters") filters: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<Product>?

    @GET("api/product/{productId}")
    suspend fun fetchProduct(
        @Path("productId") productId: ProductId,
    ): Product?

    @GET("api/product/budget")
    suspend fun fetchBudgetAssemblies(
        @Query("budget") budget: Double
    ): List<Budget>?

    @GET("api/product/filter/{productType}")
    suspend fun fetchFilters(
        @Path("productType") productType: ProductType,
    ): List<Filter>?

    @GET("api/product/search/{productType}")
    suspend fun searchProducts(
        @Path("productType") productType: ProductType,
        @Query("search") search: String,
    ): List<Product>?

    @GET("api/product/{productId}/specs")
    fun fetchProductSpec(
        productId: ProductId
    ): List<ProductSpec>?
}