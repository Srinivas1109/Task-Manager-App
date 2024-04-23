package com.benki.taskmanager.data.repository

import com.benki.taskmanager.data.dao.TaskDao
import com.benki.taskmanager.data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun insertTask(task: Task){
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    fun getTasks() : Flow<List<Task>> = taskDao.getTasks()

    fun getTaskStatusCount(status: String) : Flow<Int> = taskDao.getTaskStatusCount(status)

    fun getTasksByStatus(status: String) : Flow<List<Task>> = taskDao.getTasksByStatus(status)

}