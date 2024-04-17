package com.benki.taskmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val createdOn: Long = System.currentTimeMillis(),
    val modifiedOn: Long = System.currentTimeMillis(),
)
