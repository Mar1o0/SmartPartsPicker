package com.vlad.sharaga.network.api

import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchCityNames(): List<String> {
        return apiService.fetchCityNames()
    }

}