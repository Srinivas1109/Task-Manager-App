package com.benki.taskmanager.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.data.model.Task
import com.benki.taskmanager.data.repository.TaskRepository
import com.benki.taskmanager.data.repository.TaskWithProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskWithProjectRepository: TaskWithProjectRepository
) : ViewModel() {
    val tasksWithProject = taskWithProjectRepository.getTasksWithProject()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val tasksNotStartedCount = getTasksCount(TaskStatus.NOT_STARTED).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        10
    )

    val tasksPendingCount = getTasksCount(TaskStatus.PENDING).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        10
    )

    val tasksCompletedCount = getTasksCount(TaskStatus.COMPLETED).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        10
    )

    val tasksInProgressCount = getTasksCount(TaskStatus.IN_PROGRESS).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        10
    )

    private val _activeTask = MutableStateFlow(Task(title = "", progress = 50f))
    val activeTask = _activeTask.asStateFlow()

    private fun getTasksCount(status: TaskStatus): Flow<Int> {
        return taskRepository.getTaskStatusCount(status.name)
    }

    fun updateActiveTask(task: Task) {
        _activeTask.update { task }
    }

    fun updateTask() {
        viewModelScope.launch {
            taskRepository.updateTask(activeTask.value.copy(status = if (activeTask.value.progress > 99) TaskStatus.COMPLETED else if (activeTask.value.progress > 0) TaskStatus.IN_PROGRESS else activeTask.value.status))
            _activeTask.update { Task(title = "") }
        }
    }
}