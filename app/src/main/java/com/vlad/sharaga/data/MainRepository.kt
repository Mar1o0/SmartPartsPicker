package com.vlad.sharaga.data

import com.vlad.sharaga.data.database.tables.assembly.AssemblyDao
import com.vlad.sharaga.data.preferences.AppPreferences
import com.vlad.sharaga.network.api.ApiClient
import javax.inject.Inject

class MainRepository @Inject constructor(
    val apiClient: ApiClient,
    val appPreferences: AppPreferences,
    val assemblyDao: AssemblyDao
)