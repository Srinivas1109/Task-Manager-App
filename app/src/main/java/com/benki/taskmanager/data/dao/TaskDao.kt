package com.benki.taskmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTask(task: Task)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY modifiedOn DESC")
    fun getTasks() : Flow<List<Task>>

    @Query("SELECT COUNT(*) FROM tasks WHERE status = :status")
    fun getTaskStatusCount(status: String) : Flow<Int>
}