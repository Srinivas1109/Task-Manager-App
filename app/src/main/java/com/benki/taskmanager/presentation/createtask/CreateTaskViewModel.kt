package com.benki.taskmanager.presentation.createtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.data.model.Project
import com.benki.taskmanager.data.model.Task
import com.benki.taskmanager.data.repository.ProjectRepository
import com.benki.taskmanager.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository, private val projectRepository: ProjectRepository
) : ViewModel() {
    private val _task = MutableStateFlow(Task(title = ""))
    val task = _task.asStateFlow()

    private val _showSheet = MutableStateFlow(false)
    val showSheet = _showSheet.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker = _showDatePicker.asStateFlow()

    private val _showTimePicker = MutableStateFlow(false)
    val showTimePicker = _showTimePicker.asStateFlow()

    private val _selectedProject = MutableStateFlow(Project(name = ""))
    val selectedProject = _selectedProject.asStateFlow()

    val projects = projectRepository.getTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    fun updateTaskTitle(title: String) {
        _task.update { it.copy(title = title) }
    }

    fun updateTaskDescription(description: String) {
        _task.update { it.copy(description = description) }
    }

    fun updateTaskStatus(taskStatus: TaskStatus) {
        _task.update { it.copy(status = taskStatus) }
    }

    fun updateTaskProject(project: Project) {
        _selectedProject.update { project }
        _task.update { it.copy(projectId = project.id) }
    }

    fun toggleModalVisibility(visible: Boolean) {
        _showSheet.update { visible }
    }

    fun toggleDatePickerVisibility(visible: Boolean) {
        _showDatePicker.update { visible }
    }

    fun toggleTimePickerVisibility(visible: Boolean) {
        _showTimePicker.update { visible }
    }

    fun unLinkProject() {
        _selectedProject.update { Project(name = "") }
        _task.update { it.copy(projectId = null) }
    }

    fun updateDeadlineDate(date: Long) {
        _task.update { it.copy(deadlineDate = date) }
    }

    fun updateDeadlineTime(time: Long) {
        _task.update { it.copy(deadlineTime = time) }
    }

    fun updateReminder(time: Long) {
        _task.update { it.copy(reminder = time) }
    }

    fun toggleScheduleVisibility(visible: Boolean) {
        _task.update { it.copy(scheduled = visible) }
    }

    fun insertTask() {
        viewModelScope.launch {
            taskRepository.insertTask(
                if (task.value.scheduled) task.value else task.value.copy(
                    deadlineTime = null,
                    deadlineDate = null,
                    reminder = null
                )
            )
            _task.update { Task(title = "") }
        }
    }
}