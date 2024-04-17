package com.benki.taskmanager.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.benki.taskmanager.data.constants.TaskPriority
import com.benki.taskmanager.data.constants.TaskStatus

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = Project::class,
        parentColumns = ["id"],
        childColumns = ["projectId"],
        onDelete = ForeignKey.CASCADE,
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val projectId: Int? = null,
    val title: String,
    val description: String,
    val completed: Boolean = false,
    val deadline: Long? = null,
    val status: TaskStatus = TaskStatus.PENDING,
    val priority: TaskPriority = TaskPriority.NORMAL,
    val progress: Float = 0f,
    val reminder: Long? = null,
    val createdOn: Long = System.currentTimeMillis(),
    val modifiedOn: Long = System.currentTimeMillis(),
)
