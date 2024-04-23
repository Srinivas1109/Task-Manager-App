package com.benki.taskmanager.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benki.taskmanager.data.model.Task
import com.benki.taskmanager.data.model.TaskWithProject
import com.benki.taskmanager.utils.DateTimeUtils

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    taskWithProject: TaskWithProject,
    activeTask: Task,
    updateProgress: (Task) -> Unit,
    updateTask: () -> Unit
) {
    val task = taskWithProject.task
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = task.status.statusText,
                    color = task.status.color,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .background(
                            color = task.status.color.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 2.dp, horizontal = 8.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Progress")
                Text(
                    text = "${task.progress.toInt()}% complete",
                    color = task.status.color,
                )
            }
            Slider(
                value = if (activeTask.id == task.id) activeTask.progress else task.progress,
                onValueChange = {
                    updateProgress(task.copy(progress = it))
                },
                modifier = Modifier.fillMaxWidth(),
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    thumbColor = task.status.color,
                    activeTrackColor = task.status.color,
                    inactiveTrackColor = MaterialTheme.colorScheme.onBackground
                ),
                onValueChangeFinished = updateTask
            )
            if (task.deadlineDate != null || task.deadlineTime != null) {
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
    }
}