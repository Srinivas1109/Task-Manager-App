package com.benki.taskmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.benki.taskmanager.data.model.TaskNotification
import com.benki.taskmanager.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskNotificationDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTaskNotification(taskNotification: TaskNotification)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateTaskNotification(taskNotification: TaskNotification)

    @Delete
    suspend fun deleteTaskNotification(taskNotification: TaskNotification)

    @Query("SELECT * FROM task_notifications ORDER BY time DESC")
    fun getTaskNotifications() : Flow<List<TaskNotification>>

    @Query("SELECT * FROM task_notifications WHERE id = :id")
    fun getTaskNotificationById(id: Long) : Flow<TaskNotification>

    @Query("SELECT * FROM task_notifications WHERE read = 0")
    fun getUnreadTaskNotifications() : Flow<List<TaskNotification>>

    @Query("SELECT COUNT(*) FROM task_notifications WHERE read = 0")
    fun getUnreadTaskNotificationsCount() : Flow<Int>
}