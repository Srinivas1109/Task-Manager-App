package com.benki.taskmanager.data.repository

import com.benki.taskmanager.data.dao.TaskWithProjectDao
import com.benki.taskmanager.data.model.TaskWithProject
import kotlinx.coroutines.flow.Flow

class TaskWithProjectRepository(private val taskWithProjectDao: TaskWithProjectDao) {
    fun getTasksWithProject(): Flow<List<TaskWithProject>> =
        taskWithProjectDao.getTasksWithProject()

}