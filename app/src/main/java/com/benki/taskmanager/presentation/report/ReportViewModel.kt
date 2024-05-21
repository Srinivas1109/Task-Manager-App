package com.benki.taskmanager.presentation.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.data.model.Task
import com.benki.taskmanager.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val tasksWithStatus: Map<TaskStatus, Int> = emptyMap()
)

@HiltViewModel
class ReportViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        TaskStatus.entries.forEach { taskStatus ->
            viewModelScope.launch(context = Dispatchers.IO) {
                taskRepository.getTasksByStatus(taskStatus.name).collectLatest { tasks ->
                    Log.d("ReportViewModel", "status: ${taskStatus.statusText} tasks: ${tasks.size}")
                    _uiState.update { it.copy(tasksWithStatus = it.tasksWithStatus + (taskStatus to tasks.size)) }
                }
            }
        }
    }
}