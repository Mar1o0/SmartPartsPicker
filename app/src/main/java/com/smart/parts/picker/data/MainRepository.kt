package com.smart.parts.picker.data

import com.smart.parts.picker.data.database.tables.assembly.AssemblyDao
import com.smart.parts.picker.data.preferences.AppPreferences
import com.smart.parts.picker.network.api.ApiClient
import javax.inject.Inject

class MainRepository @Inject constructor(
    val apiClient: ApiClient,
    val appPreferences: AppPreferences,
    val assemblyDao: AssemblyDao,
)