package com.benki.taskmanager.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.benki.taskmanager.data.dao.TaskNotificationDao
import com.benki.taskmanager.data.model.TaskNotification
import com.benki.taskmanager.data.repository.TaskNotificationRepository
import com.benki.taskmanager.utils.NotificationUtils.sendNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskReceiver : BroadcastReceiver() {

    @Inject
    lateinit var taskNotificationRepository: TaskNotificationRepository

    private val coroutine = CoroutineScope(context = Dispatchers.IO)
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra("id", 0L)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val id = intent.getLongExtra("id", 1002L).toInt()
        sendNotification(context, title!!, description!!, id, taskId)
        coroutine.launch {
            taskNotificationRepository.insertTaskNotification(
                TaskNotification(
                    title = title,
                    time = System.currentTimeMillis(),
                    taskId = taskId
                )
            )
        }
    }
}