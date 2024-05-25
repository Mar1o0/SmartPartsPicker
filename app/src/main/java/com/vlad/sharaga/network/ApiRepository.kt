package com.vlad.sharaga.network

import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.network.api.ApiClient
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiClient: ApiClient
) {

    suspend fun fetchCityNames(): List<String> {
        return apiClient.fetchCityNames()
    }

    suspend fun fetchHomeFeed(): List<Item> {
        return apiClient.fetchHomeFeed()
    }

}