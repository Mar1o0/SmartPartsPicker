package com.vlad.sharaga.network.api

import com.vlad.sharaga.core.adapter.recycler.fingerprints.BudgetAssemblyItem
import com.vlad.sharaga.core.adapter.spinner.SearchResultItem
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.models.Filter
import com.vlad.sharaga.models.Product
import com.vlad.sharaga.models.ProductImage
import com.vlad.sharaga.models.ProductPrice
import com.vlad.sharaga.models.ProductSpec
import com.vlad.sharaga.models.ProductType
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/cities/")
    suspend fun fetchCityNames(): List<String>?

    @GET("/product/")
    suspend fun fetchProduct(
        @Query("productId") productId: ProductId
    ): Product?

    @GET("/products/")
    suspend fun fetchProducts(
        @Query("productType") productType: String
    ): List<Product>?

    @GET("/product_prices/")
    suspend fun fetchProductPrices(
        @Query("productId") productId: ProductId
    ): List<ProductPrice>?

    @GET("/product_image/")
    suspend fun fetchProductImage(
        @Query("productId") productId: ProductId
    ): ProductImage?

    @GET("/product_images/")
    suspend fun fetchProductImages(
        @Query("productId") productId: ProductId
    ): List<ProductImage>?

    @GET("/product_specs/")
    suspend fun fetchProductSpecs(
        @Query("productId") productId: ProductId
    ): List<ProductSpec>?

    @GET("/filters/")
    suspend fun fetchFilters(
        @Query("productType") productType: ProductType
    ): List<Filter>?

    @GET("/assemblies/")
    suspend fun fetchAssemblies(
        @Query("budgetPrice") budgetPrice: Int
    ): List<BudgetAssemblyItem>?

    @GET("/search/")
    suspend fun searchProducts(
        @Query("query") query: String
    ): List<SearchResultItem>?
}