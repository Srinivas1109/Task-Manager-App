package com.benki.taskmanager.presentation.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.data.model.BarData
import com.benki.taskmanager.data.repository.ProjectRepository
import com.benki.taskmanager.data.repository.TaskRepository
import com.benki.taskmanager.data.repository.TaskWithProjectRepository
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
    val allTasksBarChartData: List<BarData> = emptyList(),
    val projectsBarChartData: Map<String, List<BarData>> = emptyMap()
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val projectRepository: TaskWithProjectRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        TaskStatus.entries.forEach { taskStatus ->
            viewModelScope.launch(context = Dispatchers.IO) {
                taskRepository.getTasksByStatus(taskStatus.name).collectLatest { tasks ->
                    if (tasks.isNotEmpty()) {
                        _uiState.update {
                            it.copy(
                                allTasksBarChartData = it.allTasksBarChartData + BarData(
                                    taskStatus.statusText,
                                    tasks.size,
                                    barColor = taskStatus.color
                                )
                            )
                        }
                    }
                }
            }
        }
        getProjectsBarChartData()
    }

    private fun getProjectsBarChartData() {
        viewModelScope.launch(context = Dispatchers.IO) {
            projectRepository.getTaskReports().collectLatest { tasksWithProjectList ->
                tasksWithProjectList.groupBy { it.project?.name }.map { (projectName, tasks) ->
                    val tasksByStatus = TaskStatus.entries.map { taskStatus ->
                        BarData(
                            label = taskStatus.statusText,
                            value = tasks.count { it.task.status.statusText == taskStatus.statusText },
                            barColor = taskStatus.color
                        )
                    }.filter { it.value > 0 }
                    _uiState.update { it.copy(projectsBarChartData = it.projectsBarChartData + (projectName!! to tasksByStatus)) }
                }
            }
        }
    }
}