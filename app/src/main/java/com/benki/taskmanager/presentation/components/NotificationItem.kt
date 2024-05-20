package com.benki.taskmanager.presentation.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.benki.taskmanager.data.model.TaskNotification
import com.benki.taskmanager.utils.DateTimeUtils.convertMillisToDateTime
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    taskNotification: TaskNotification,
    deleteNotification: (TaskNotification) -> Unit,
    navigateToDetail: (Long) -> Unit
) {
    var removed by remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(confirmValueChange = { dismissValue ->
        removed = dismissValue == DismissValue.DismissedToEnd
        true
    })
    AnimatedVisibility(
        visible = !removed,
        exit = shrinkVertically(animationSpec = tween(1500)) + fadeOut()
    ) {
        SwipeToDismiss(
            state = dismissState,
            background = {
                Card(
                    modifier = modifier.padding(horizontal = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(imageVector = Icons.Filled.DeleteSweep, contentDescription = null)
                        Text(
                            text = "Delete", modifier = modifier
                                .padding(16.dp)
                        )
                    }
                }
            },
            dismissContent = {
                Card(
                    onClick = {
                        navigateToDetail(taskNotification.taskId)
                    },
                    modifier = modifier.padding(horizontal = 8.dp)
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = taskNotification.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = convertMillisToDateTime(taskNotification.time),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        IconButton(
                            onClick = { deleteNotification(taskNotification) },
                            modifier = modifier.size(50.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Cancel, contentDescription = null)
                        }
                    }

                }
            },
            directions = setOf(DismissDirection.StartToEnd)
        )
    }

    LaunchedEffect(key1 = removed) {
        if (removed) {
            delay(1000)
            deleteNotification(taskNotification)
        }
    }
}