package com.vlad.sharaga.network.api

interface ApiService {

    suspend fun fetchCityNames(): List<String>

}

class ApiServiceImpl : ApiService {

    override suspend fun fetchCityNames(): List<String> {
        return listOf(
            "Гродно", "Минск", "Могилев", "Витебск", "Брест", "Гомель"
        )
    }

}