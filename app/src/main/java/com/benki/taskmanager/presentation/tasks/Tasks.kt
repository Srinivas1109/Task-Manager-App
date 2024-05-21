package com.benki.taskmanager.presentation.tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benki.taskmanager.R
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.presentation.components.TaskItem

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Tasks(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
    status: String,
    navigateToDetail: (Long) -> Unit,
    navigateBack: () -> Unit
) {
    val taskStatus = remember {
        TaskStatus.valueOf(status)
    }
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    LazyColumn(modifier = modifier.fillMaxSize()) {
        stickyHeader {
            TopAppBar(
                title = {
                    Text(
                        text = "${taskStatus.statusText} Tasks (${tasks.size})",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = taskStatus.color,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
        if (tasks.isNotEmpty()) {
            items(items = tasks) { task ->
                TaskItem(task = task) {
                    navigateToDetail(task.id)
                }
            }
        } else {
            item {
                Box(
                    modifier = Modifier
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val message = when (TaskStatus.valueOf(status)) {
                        TaskStatus.COMPLETED -> R.string.no_completed_tasks
                        TaskStatus.IN_PROGRESS -> R.string.in_progress_tasks
                        TaskStatus.NOT_STARTED -> R.string.not_started_tasks
                        TaskStatus.PENDING -> R.string.pending_tasks
                        TaskStatus.CANCELLED -> R.string.cancelled_tasks
                        TaskStatus.NEEDS_REVIEW -> R.string.needs_review_tasks
                    }

                    Text(
                        text = stringResource(id = message),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                }
            }
        }

    }

    LaunchedEffect(key1 = status) {
        viewModel.getTasks(taskStatus)
    }
}