package com.benki.taskmanager.data.repository

import com.benki.taskmanager.data.dao.TaskNotificationDao
import com.benki.taskmanager.data.model.TaskNotification
import kotlinx.coroutines.flow.Flow

class TaskNotificationRepository(private val taskNotificationDao: TaskNotificationDao) {
    suspend fun insertTaskNotification(task: TaskNotification){
        taskNotificationDao.insertTaskNotification(task)
    }

    suspend fun deleteTaskNotification(task: TaskNotification){
        taskNotificationDao.deleteTaskNotification(task)
    }

    suspend fun updateTaskNotification(task: TaskNotification){
        taskNotificationDao.updateTaskNotification(task)
    }

    fun getTaskNotifications() : Flow<List<TaskNotification>> = taskNotificationDao.getTaskNotifications()

    fun getTaskNotificationById(id: Long) : Flow<TaskNotification> = taskNotificationDao.getTaskNotificationById(id)

    fun getUnReadTaskNotifications() : Flow<List<TaskNotification>> = taskNotificationDao.getUnreadTaskNotifications()
    fun getUnReadTaskNotificationsCount() : Flow<Int> = taskNotificationDao.getUnreadTaskNotificationsCount()
}