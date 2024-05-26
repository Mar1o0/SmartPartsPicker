package com.vlad.sharaga.data

import com.vlad.sharaga.data.preferences.AppPreferences
import com.vlad.sharaga.network.ApiRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    val apiRepository: ApiRepository,
    val appPreferences: AppPreferences
)