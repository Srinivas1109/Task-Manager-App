package com.benki.taskmanager.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.benki.taskmanager.utils.NotificationUtils.sendNotification

class TaskReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val id = intent.getLongExtra("id", 1002L).toInt()
        sendNotification(context, title!!, description!!, id)
    }
}