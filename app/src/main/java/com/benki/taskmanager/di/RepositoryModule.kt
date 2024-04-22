package com.benki.taskmanager.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.benki.taskmanager.data.dao.ProjectDao
import com.benki.taskmanager.data.dao.TaskDao
import com.benki.taskmanager.data.dao.TaskWithProjectDao
import com.benki.taskmanager.data.repository.ProjectRepository
import com.benki.taskmanager.data.repository.TaskRepository
import com.benki.taskmanager.data.repository.TaskWithProjectRepository
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

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepository(taskDao = taskDao)

    @Provides
    fun provideProjectRepository(projectDao: ProjectDao): ProjectRepository =
        ProjectRepository(projectDao = projectDao)

    @Provides
    fun provideTaskWithProjectRepository(taskWithProjectDao: TaskWithProjectDao): TaskWithProjectRepository =
        TaskWithProjectRepository(taskWithProjectDao = taskWithProjectDao)
}