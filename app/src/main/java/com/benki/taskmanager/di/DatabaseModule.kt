package com.benki.taskmanager.di

import android.content.Context
import androidx.room.Room
import com.benki.taskmanager.data.dao.ProjectDao
import com.benki.taskmanager.data.dao.TaskDao
import com.benki.taskmanager.data.dao.TaskNotificationDao
import com.benki.taskmanager.data.dao.TaskWithProjectDao
import com.benki.taskmanager.data.database.TaskManagerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTaskManagerDatabase(@ApplicationContext context: Context): TaskManagerDatabase {
        return Room.databaseBuilder(context, TaskManagerDatabase::class.java, "TaskManager").build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: TaskManagerDatabase): TaskDao = database.taskDao()

    @Provides
    @Singleton
    fun provideProjectDao(database: TaskManagerDatabase): ProjectDao = database.projectDao()

    @Provides
    @Singleton
    fun provideTaskWithProjectDao(database: TaskManagerDatabase): TaskWithProjectDao = database.tasksWithProjectDao()

    @Provides
    @Singleton
    fun provideTaskNotificationDao(database: TaskManagerDatabase): TaskNotificationDao = database.taskNotificationDao()
}