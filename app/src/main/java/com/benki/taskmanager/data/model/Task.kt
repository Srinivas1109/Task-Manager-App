package com.benki.taskmanager.data.model

import com.benki.taskmanager.data.constants.TaskPriority
import com.benki.taskmanager.data.constants.TaskStatus

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val completed: Boolean = false,
    val deadline: Long? = null,
    val status: String = TaskStatus.PENDING.statusText,
    val priority: String = TaskPriority.NORMAL.priorityText,
    val progress: Float = 0f,
    val reminder: Long? = null,
    val createdOn: Long = System.currentTimeMillis(),
    val modifiedOn: Long = System.currentTimeMillis(),
)
