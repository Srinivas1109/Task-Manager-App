package com.benki.taskmanager.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    val visitedOnBoarding: Flow<Boolean> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[VISITED_ONBOARDING] ?: false
    }

    suspend fun updateOnBoardingVisit(visited: Boolean) {
        dataStore.edit { preferences ->
            preferences[VISITED_ONBOARDING] = visited
        }
    }

    companion object {
        private val VISITED_ONBOARDING = booleanPreferencesKey("visited_onboarding")
    }
}