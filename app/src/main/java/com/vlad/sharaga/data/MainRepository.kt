package com.vlad.sharaga.data

import androidx.datastore.preferences.core.Preferences
import com.vlad.sharaga.data.preferences.AppPreferences
import com.vlad.sharaga.network.ApiRepository
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiRepository: ApiRepository,
    private val appPreferences: AppPreferences
) {
    suspend fun fetchCityNames(): List<String> {
        return apiRepository.fetchCityNames()
    }

    suspend fun <T> savePref(key: Preferences.Key<T>, value: T): Unit =
        appPreferences.save(key, value)

    suspend fun <T> getPref(key: Preferences.Key<T>): T? =
        appPreferences.get(key)

    suspend fun <T> getPref(key: Preferences.Key<T>, default: T): T =
        appPreferences.get(key, default)
}