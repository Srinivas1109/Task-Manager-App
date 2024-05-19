package com.benki.taskmanager.presentation.task_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benki.taskmanager.data.constants.AppConstants
import com.benki.taskmanager.data.constants.TaskStatus
import com.benki.taskmanager.presentation.components.PrimaryButton
import com.benki.taskmanager.presentation.components.TaskTextFiled
import com.benki.taskmanager.presentation.components.TimePickerDialog
import com.benki.taskmanager.utils.DateTimeUtils
import com.benki.taskmanager.utils.DateTimeUtils.convertMillisToTime
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    taskId: Long,
    navigateBack: () -> Unit,
    viewModel: DetailedTaskViewModel = hiltViewModel()
) {
    val task by viewModel.task.collectAsStateWithLifecycle()
    val projects by viewModel.projects.collectAsStateWithLifecycle()
    val showSheet by viewModel.showSheet.collectAsStateWithLifecycle()
    val showDatePicker by viewModel.showDatePicker.collectAsStateWithLifecycle()
    val showTimePicker by viewModel.showTimePicker.collectAsStateWithLifecycle()
    val selectedProject by viewModel.selectedProject.collectAsStateWithLifecycle()
    val modalBottomSheetScaffoldState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    val timePickerState =
        rememberTimePickerState(
            initialHour = Calendar.getInstance().get(Calendar.HOUR),
            initialMinute = 0,
            is24Hour = false
        )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = modifier.fillMaxWidth()) {
                IconButton(
                    onClick = navigateBack,
                    modifier = modifier
                        .align(Alignment.TopStart)
                        .size(40.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
                }
                Text(
                    text = "Update task",
                    modifier = modifier.align(Alignment.TopCenter),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp
                )
            }
            Spacer(modifier = modifier.height(40.dp))
            TaskTextFiled(
                value = task.title,
                title = "Task title",
                onValueChange = viewModel::updateTaskTitle,
                placeholder = "Type here..."
            )
            Spacer(modifier = modifier.height(30.dp))
            TaskTextFiled(
                value = task.description,
                title = "Task description",
                onValueChange = viewModel::updateTaskDescription,
                placeholder = "Type here...",
                modifier = modifier.heightIn(min = 150.dp, max = 250.dp)
            )

            Column(modifier = modifier.fillMaxWidth()) {
                Spacer(modifier = modifier.heightIn(24.dp))
                Text(text = "Task Status")
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    TaskStatus.entries.forEach { taskStatus ->
                        AssistChip(onClick = { viewModel.updateTaskStatus(taskStatus) },
                            label = { Text(text = taskStatus.statusText) },
                            modifier = modifier.padding(horizontal = 4.dp),
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = taskStatus.color, labelColor = Color.White
                            ),
                            border = null,
                            leadingIcon = {
                                if (task.status.statusText == taskStatus.statusText) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            })
                    }
                }
            }

            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "${task.progress.toInt()}% complete",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Slider(
                value = task.progress,
                valueRange = 0f..100f,
                onValueChange = viewModel::updateTaskProgress
            )

            Column(modifier = modifier.fillMaxWidth()) {
                Spacer(modifier = modifier.heightIn(24.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ), shape = RoundedCornerShape(8.dp), modifier = modifier
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (task.projectId == null) "Link to project" else "Linked to ${selectedProject.name}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (task.projectId != null) {
                            IconButton(onClick = { viewModel.unLinkProject() }) {
                                Icon(
                                    imageVector = Icons.Filled.LinkOff, contentDescription = null
                                )
                            }
                        } else {
                            IconButton(onClick = { viewModel.toggleModalVisibility(true) }) {
                                Icon(
                                    imageVector = Icons.Filled.Link, contentDescription = null
                                )
                            }
                        }
                    }
                }
                if (showSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { viewModel.toggleModalVisibility(false) },
                        sheetState = modalBottomSheetScaffoldState
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            if (projects.isEmpty()) {
                                Text(
                                    text = "You don't have any projects, click on the below button to create new selectedProject.",
                                    modifier = modifier.fillMaxWidth()
                                )
                                Spacer(modifier = modifier.heightIn(16.dp))
                                PrimaryButton(
                                    text = "Create Project", onClick = {}
                                )
                            } else {
                                Text(
                                    text = "Choose selectedProject",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 18.dp),
                                    textAlign = TextAlign.Center
                                )
                                projects.forEach { project ->
                                    Card(
                                        modifier = modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .padding(vertical = 8.dp)
                                            .clickable {
                                                viewModel.updateTaskProject(project)
                                                coroutineScope.launch {
                                                    viewModel.toggleModalVisibility(false)
                                                    modalBottomSheetScaffoldState.hide()
                                                }
                                            }, colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                        ), shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Row(
                                            modifier = modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Column(modifier = modifier.fillMaxWidth(0.75f)) {
                                                Text(text = project.name)
                                                Text(text = project.description, fontSize = 12.sp)
                                            }
                                            Icon(
                                                imageVector = Icons.Default.KeyboardDoubleArrowRight,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.scheduled,
                    onCheckedChange = {
                        viewModel.toggleScheduleVisibility(it)
                        viewModel.updateDeadlineDate(datePickerState.selectedDateMillis!!)
                        viewModel.updateDeadlineTime(
                            DateTimeUtils.convertTimeToMillis(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                        )
                    },
                )
                Text(text = "Schedule task")
            }
            AnimatedVisibility(visible = task.scheduled) {
                Column(modifier = modifier.fillMaxWidth()) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(modifier = modifier.clickable {
                            viewModel.toggleDatePickerVisibility(
                                true
                            )
                        }) {
                            Column(modifier = modifier.padding(16.dp)) {
                                Text(text = "Deadline Date")
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = modifier.padding(vertical = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.CalendarMonth,
                                        contentDescription = null
                                    )
                                    task.deadlineDate?.let { date ->
                                        Text(text = DateTimeUtils.convertLongToDate(date))
                                    }
                                }
                            }
                        }
                        Card(modifier = modifier.clickable {
                            viewModel.toggleTimePickerVisibility(
                                true
                            )
                        }) {
                            Column(modifier = modifier.padding(16.dp)) {
                                Text(text = "Deadline Time")
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = modifier.padding(vertical = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AccessTime,
                                        contentDescription = null
                                    )
                                    task.deadlineTime?.let { time ->
                                        Text(text = convertMillisToTime(time))
                                    }
                                }
                            }
                        }

                    }

                    Text(text = "Remind me before ")
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        AppConstants.REMINDER_MILLISECONDS.forEach { millis ->
                            AssistChip(
                                onClick = { viewModel.updateReminder(millis) },
                                label = {
                                    Text(
                                        text = "${DateTimeUtils.convertMillsToMinutes(millis)} mins"
                                    )
                                },
                                leadingIcon = {
                                    task.reminder?.let {
                                        if (it == millis) {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                },
                            )
                        }
                    }

                }
            }

            if (showDatePicker) {
                DatePickerDialog(onDismissRequest = { viewModel.toggleDatePickerVisibility(false) },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.toggleDatePickerVisibility(false)
                            datePickerState.selectedDateMillis?.let {
                                viewModel.updateDeadlineDate(
                                    it
                                )
                            }
                        }) {
                            Text(text = "Ok")
                        }
                    }) {
                    DatePicker(state = datePickerState, title = {
                        Text(
                            text = "Choose deadline date", modifier = modifier.padding(18.dp)
                        )
                    }, dateValidator = {
                        it >= System.currentTimeMillis() - 24 * 60 * 60 * 1000
                    })
                }
            }

            if (showTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { viewModel.toggleTimePickerVisibility(false) },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.toggleTimePickerVisibility(false)
                            viewModel.updateDeadlineTime(
                                DateTimeUtils.convertTimeToMillis(
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                            )
                        }) {
                            Text(text = "Ok")
                        }
                    }) {
                    TimePicker(state = timePickerState)
                }
            }
        }
        PrimaryButton(text = "Update task", enabled = task.title.isNotEmpty()) {
            viewModel.updateTask()
            navigateBack()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getTask(taskId)
    }

    LaunchedEffect(key1 = task.projectId) {
        if (task.projectId != null) {
            viewModel.getProject(task.projectId!!)
        }
    }
}