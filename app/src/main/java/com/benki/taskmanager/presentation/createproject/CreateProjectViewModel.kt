package com.benki.taskmanager.presentation.createproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.taskmanager.data.model.Project
import com.benki.taskmanager.data.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProjectViewModel @Inject constructor(private val projectRepository: ProjectRepository) : ViewModel() {
    private val _project = MutableStateFlow(Project(name = ""))
    val project = _project.asStateFlow()

    fun updateProjectName(name: String) {
        _project.update { it.copy(name = name) }
    }

    fun updateProjectDescription(description: String) {
        _project.update { it.copy(description = description) }
    }

    fun insertProject(){
        viewModelScope.launch {
            projectRepository.insertProject(project.value)
            _project.update { Project(name = "") }
        }
    }
}