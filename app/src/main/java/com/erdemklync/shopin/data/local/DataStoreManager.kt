package com.erdemklync.shopin.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "app_settings")

class DataStoreManager(context: Context) {

    companion object PreferencesKeys {
        val FIRST_TIME = booleanPreferencesKey("first_time")
    }

    private val dataStore = context.dataStore

    suspend fun setFirstTime(firstTime: Boolean) {
        dataStore.edit { preferences ->
            preferences[FIRST_TIME] = firstTime
        }
    }

    val getFirstTime: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[FIRST_TIME] ?: false
    }

}