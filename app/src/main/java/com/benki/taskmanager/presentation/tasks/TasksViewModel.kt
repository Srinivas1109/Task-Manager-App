package com.benki.taskmanager.presentation.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.data.model.Task
import com.benki.taskmanager.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow(emptyList<Task>())
    val tasks: StateFlow<List<Task>> =
        _tasks.asStateFlow()

    suspend fun getTasks(taskStatus: TaskStatus) = viewModelScope.launch {
        taskRepository.getTasksByStatus(status = taskStatus.name).collectLatest { tasksLocal ->
            _tasks.update { tasksLocal }
        }
    }
}