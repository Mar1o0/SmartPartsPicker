package com.vlad.sharaga.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.appPreferences: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AppPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences = context.appPreferences

    suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        preferences.edit { settings ->
            settings[key] = value
        }
    }

    suspend fun <T> get(key: Preferences.Key<T>): T? {
        return preferences.data
            .map { pref -> pref[key] }
            .firstOrNull()
    }

    suspend fun <T> get(key: Preferences.Key<T>, default: T): T {
        return preferences.data
            .map { pref -> pref[key] }
            .firstOrNull() ?: default
    }

}