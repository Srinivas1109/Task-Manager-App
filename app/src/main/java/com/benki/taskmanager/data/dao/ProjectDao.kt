package com.benki.taskmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.benki.taskmanager.data.model.Project
import com.benki.taskmanager.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertProject(project: Project)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateProject(project: Project)

    @Delete
    suspend fun deleteProject(project: Project)

    @Query("SELECT * FROM projects ORDER BY modifiedOn DESC")
    fun getProjects() : Flow<List<Project>>

    @Query("SELECT * FROM projects WHERE id = :id")
    fun getProjectById(id: Long) : Flow<Project>
}