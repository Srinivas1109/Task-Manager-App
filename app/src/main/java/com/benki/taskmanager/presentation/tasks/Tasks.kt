package com.benki.taskmanager.presentation.tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benki.taskmanager.data.constants.TaskStatus

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Tasks(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
    status: String,
    navigateBack: () -> Unit
) {
    val taskStatus = TaskStatus.valueOf(status)
    val tasks by viewModel.getTasks(taskStatus)
        .collectAsStateWithLifecycle(initialValue = emptyList())

    LazyColumn(modifier = modifier.fillMaxWidth()) {
        stickyHeader {
            TopAppBar(
                title = { Text(text = "${taskStatus.statusText} Tasks (${tasks.size})", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack) {
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
    }

}