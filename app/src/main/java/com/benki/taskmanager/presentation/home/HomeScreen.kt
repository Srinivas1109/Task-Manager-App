package com.benki.taskmanager.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benki.taskmanager.R
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.presentation.components.TaskCountItem
import com.benki.taskmanager.presentation.components.TaskItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToTasks: (TaskStatus) -> Unit,
) {
    val tasksWithProject by viewModel.tasksWithProject.collectAsStateWithLifecycle()
    val tasksNotStartedCount by viewModel.tasksNotStartedCount.collectAsStateWithLifecycle()
    val tasksCompletedCount by viewModel.tasksCompletedCount.collectAsStateWithLifecycle()
    val tasksPendingCount by viewModel.tasksPendingCount.collectAsStateWithLifecycle()
    val tasksInProgressCount by viewModel.tasksInProgressCount.collectAsStateWithLifecycle()
    val activeTask by viewModel.activeTask.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .safeDrawingPadding()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
            .fillMaxSize()
    ) {
        LazyColumn {
            item(key = "task status header") {
                Column {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TaskCountItem(
                            modifier = modifier.weight(1f),
                            title = TaskStatus.NOT_STARTED.statusText,
                            count = tasksNotStartedCount,
                            containerColor = TaskStatus.NOT_STARTED.color
                        ) {
                            navigateToTasks(TaskStatus.NOT_STARTED)
                        }

                        TaskCountItem(
                            modifier = modifier.weight(1f),
                            title = TaskStatus.IN_PROGRESS.statusText,
                            count = tasksInProgressCount,
                            containerColor = TaskStatus.IN_PROGRESS.color
                        ) {
                            navigateToTasks(TaskStatus.IN_PROGRESS)
                        }
                    }
                    Spacer(modifier = modifier.height(24.dp))
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TaskCountItem(
                            modifier = modifier.weight(1f),
                            title = TaskStatus.COMPLETED.statusText,
                            count = tasksCompletedCount,
                            containerColor = TaskStatus.COMPLETED.color
                        ) {
                            navigateToTasks(TaskStatus.COMPLETED)
                        }

                        TaskCountItem(
                            modifier = modifier.weight(1f),
                            title = TaskStatus.PENDING.statusText,
                            count = tasksPendingCount,
                            containerColor = TaskStatus.PENDING.color
                        ) {
                            navigateToTasks(TaskStatus.PENDING)
                        }
                    }
                    Spacer(modifier = modifier.height(24.dp))
                    Text(text = "Latest task report", fontSize = 18.sp)
                    Spacer(modifier = modifier.height(16.dp))
                }
            }
            items(items = tasksWithProject, key = {it.task.id}) {
                TaskItem(
                    taskWithProject = it,
                    activeTask = activeTask,
                    updateProgress = viewModel::updateActiveTask,
                    updateTask = viewModel::updateTask
                )
            }
        }
    }
}