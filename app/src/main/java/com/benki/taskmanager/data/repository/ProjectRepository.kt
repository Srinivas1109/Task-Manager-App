package com.benki.taskmanager.data.repository

import com.benki.taskmanager.data.dao.ProjectDao
import com.benki.taskmanager.data.dao.TaskDao
import com.benki.taskmanager.data.model.Project
import com.benki.taskmanager.data.model.Task
import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {
    suspend fun insertProject(project: Project) {
        projectDao.insertProject(project)
    }

    suspend fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }

    suspend fun updateProject(project: Project) {
        projectDao.updateProject(project)
    }

    fun getTasks(): Flow<List<Project>> = projectDao.getProjects()

}