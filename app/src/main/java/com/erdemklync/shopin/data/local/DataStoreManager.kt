package com.erdemklync.shopin.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.erdemklync.shopin.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = Constants.APP_SETTINGS)

class DataStoreManager(context: Context) {

    companion object PreferencesKeys {
        val FIRST_TIME = booleanPreferencesKey(Constants.SETTINGS_FIRST_TIME)
    }

    private val dataStore = context.dataStore

    suspend fun setFirstTime(firstTime: Boolean) {
        dataStore.edit { preferences ->
            preferences[FIRST_TIME] = firstTime
        }
    }

    val getFirstTime: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[FIRST_TIME] ?: true
    }

}