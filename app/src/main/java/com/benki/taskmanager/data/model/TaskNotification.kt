package com.benki.taskmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_notifications")
data class TaskNotification(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val read: Boolean = false,
    val time: Long,
    val taskId: Long
)
