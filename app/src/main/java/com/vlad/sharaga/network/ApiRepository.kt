package com.vlad.sharaga.network

import com.vlad.sharaga.network.api.ApiClient
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiClient: ApiClient
) {

    suspend fun fetchCityNames(): List<String> {
        return apiClient.fetchCityNames()
    }

}