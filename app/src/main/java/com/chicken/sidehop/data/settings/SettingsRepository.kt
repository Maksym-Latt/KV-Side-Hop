package com.chicken.sidehop.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore("chicken_settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val soundEnabledKey = booleanPreferencesKey("sound_enabled")
    private val musicEnabledKey = booleanPreferencesKey("music_enabled")

    val isSoundEnabled: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[soundEnabledKey] ?: true
    }

    val isMusicEnabled: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[musicEnabledKey] ?: true
    }

    suspend fun updateSound(enabled: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[soundEnabledKey] = enabled
        }
    }

    suspend fun updateMusic(enabled: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[musicEnabledKey] = enabled
        }
    }
}
