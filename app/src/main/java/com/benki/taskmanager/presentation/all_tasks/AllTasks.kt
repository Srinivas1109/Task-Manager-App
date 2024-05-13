package com.benki.taskmanager.presentation.all_tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benki.taskmanager.presentation.components.TaskItem

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AllTasks(
    modifier: Modifier = Modifier,
    viewModel: AllTasksViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToTask: (Long) -> Unit
) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        stickyHeader {
            TopAppBar(
                title = { Text(text = "Tasks", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }

        items(items = tasks, key = { it.id }) { task ->
            TaskItem(task = task) {
                navigateToTask(task.id)
            }
        }
    }
}