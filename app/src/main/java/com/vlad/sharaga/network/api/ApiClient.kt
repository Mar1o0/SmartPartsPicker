package com.vlad.sharaga.network.api

import com.vlad.sharaga.domain.adapter.recycler.Item
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchCityNames(): List<String> {
        return apiService.fetchCityNames()
    }

    suspend fun fetchHomeFeed(): List<Item> {
        return apiService.fetchHomeFeed()
    }

    suspend fun fetchCatalogItems(): List<Item> {
        return apiService.fetchCatalogItems()
    }

    suspend fun fetchGames(): List<Item> {
        return apiService.fetchGames()
    }

    suspend fun fetchCategory(categoryId: String): List<Item> {
        return apiService.fetchCategory(categoryId)
    }
}