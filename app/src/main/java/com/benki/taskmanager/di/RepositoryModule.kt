package com.benki.taskmanager.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.benki.taskmanager.data.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun provideUserPreferenceRepository(dataStore: DataStore<Preferences>): UserPreferencesRepository =
        UserPreferencesRepository(dataStore)
}