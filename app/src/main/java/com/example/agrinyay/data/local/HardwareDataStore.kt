package com.example.agrinyay.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "hardware_prefs")

class HardwareDataStore(private val context: Context) {

    companion object {
        private val HARDWARE_KEY = stringSetPreferencesKey("hardware_ids")
    }

    suspend fun saveHardwareId(hardwareId: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[HARDWARE_KEY] ?: emptySet()
            preferences[HARDWARE_KEY] = current + hardwareId
        }
    }

    fun getHardwareIds(): Flow<List<String>> {
        return context.dataStore.data.map { preferences ->
            preferences[HARDWARE_KEY]?.toList() ?: emptyList()
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.remove(HARDWARE_KEY)
        }
    }
}
