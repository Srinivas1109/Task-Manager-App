package com.benki.taskmanager.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benki.taskmanager.data.dao.ProjectDao
import com.benki.taskmanager.data.dao.TaskDao
import com.benki.taskmanager.data.dao.TaskNotificationDao
import com.benki.taskmanager.data.dao.TaskWithProjectDao
import com.benki.taskmanager.data.model.Project
import com.benki.taskmanager.data.model.Task
import com.benki.taskmanager.data.model.TaskNotification

@Database(entities = [Project::class, Task::class, TaskNotification::class], version = 2, exportSchema = false)
abstract class TaskManagerDatabase : RoomDatabase() {
    abstract fun projectDao() : ProjectDao
    abstract fun taskDao(): TaskDao
    abstract fun tasksWithProjectDao() : TaskWithProjectDao
    abstract fun taskNotificationDao() : TaskNotificationDao
}