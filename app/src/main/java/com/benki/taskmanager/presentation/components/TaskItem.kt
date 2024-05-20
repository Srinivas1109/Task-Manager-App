package com.benki.taskmanager.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.benki.taskmanager.data.model.Task
import com.benki.taskmanager.utils.DateTimeUtils

@Composable
fun TaskItem(modifier: Modifier = Modifier, task: Task, navigateToDetail: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                navigateToDetail()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = task.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                )
                if (task.deadlineDate != null || task.deadlineTime != null) {
                    Spacer(modifier = modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Timer, contentDescription = null)
                        task.deadlineDate?.let {
                            Text(text = DateTimeUtils.convertLongToDate(it))
                        }
                        task.deadlineTime?.let {
                            Text(text = DateTimeUtils.convertMillisToTime(it))
                        }
                    }
                }
            }
            Column(
                modifier = modifier.width(60.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    progress = task.progress / 100f,
                    color = task.status.color,
                    trackColor = MaterialTheme.colorScheme.outline,
                    modifier = modifier.size(45.dp)
                )
                Text(
                    text = "${task.progress.toInt()} %",
                    color = task.status.color,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
                Text(
                    text = "progress",
                    color = task.status.color,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }

        }
    }
}