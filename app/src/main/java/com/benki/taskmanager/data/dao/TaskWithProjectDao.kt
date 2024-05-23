package com.benki.taskmanager.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.benki.taskmanager.data.model.TaskWithProject
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskWithProjectDao {
    @Query("SELECT * FROM tasks ORDER BY modifiedOn DESC")
    fun getTasksWithProject(): Flow<List<TaskWithProject>>

    @Query("SELECT * FROM tasks WHERE projectId IS NOT NULL ORDER BY modifiedOn DESC")
    fun getTaskReports(): Flow<List<TaskWithProject>>
}