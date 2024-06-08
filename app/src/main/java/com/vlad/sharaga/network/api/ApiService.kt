package com.vlad.sharaga.network.api

import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.models.Filter
import com.vlad.sharaga.models.Product
import com.vlad.sharaga.models.ProductImage
import com.vlad.sharaga.models.ProductPrice
import com.vlad.sharaga.models.ProductSpec
import com.vlad.sharaga.models.ProductType
import retrofit2.http.GET

interface ApiService {

    @GET("/cities/")
    suspend fun fetchCityNames(): List<String>?

    @GET("/product/")
    suspend fun fetchProduct(productId: ProductId): Product?

    @GET("/products/")
    suspend fun fetchProducts(productType: String): List<Product>?

    @GET("/product_prices/")
    suspend fun fetchProductPrices(productId: ProductId): List<ProductPrice>?

    @GET("/product_image/")
    suspend fun fetchProductImage(productId: ProductId): ProductImage?

    @GET("/product_images/")
    suspend fun fetchProductImages(productId: ProductId): List<ProductImage>?

    @GET("/product_specs/")
    suspend fun fetchProductSpecs(productId: ProductId): List<ProductSpec>?

    @GET("/filters/")
    suspend fun fetchFilters(productType: ProductType): List<Filter>?
}