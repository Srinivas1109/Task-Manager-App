package com.benki.taskmanager.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benki.taskmanager.data.dao.ProjectDao
import com.benki.taskmanager.data.dao.TaskDao
import com.benki.taskmanager.data.model.Project
import com.benki.taskmanager.data.model.Task

@Database(entities = [Project::class, Task::class], version = 1, exportSchema = false)
abstract class TaskManagerDatabase : RoomDatabase() {
    abstract fun projectDao() : ProjectDao
    abstract fun taskDao(): TaskDao
}