package com.benki.taskmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.benki.taskmanager.broadcast.TaskReceiver
import com.benki.taskmanager.data.model.Task
import java.util.Calendar

class TaskScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    fun scheduleTask(task: Task) {

        if (task.deadlineDate != null && task.deadlineTime != null) {
            val calendarDate = Calendar.getInstance().apply { timeInMillis = task.deadlineDate!! }
            val calendarTime = Calendar.getInstance().apply { timeInMillis = task.deadlineTime!! }

            calendarTime.set(Calendar.YEAR, calendarDate.get(Calendar.YEAR))
            calendarTime.set(Calendar.MONTH, calendarDate.get(Calendar.MONTH))
            calendarTime.set(Calendar.DAY_OF_MONTH, calendarDate.get(Calendar.DAY_OF_MONTH))

            val durationInMillis =
                if (task.reminder != null) calendarTime.timeInMillis - task.reminder else calendarTime.timeInMillis

            if (durationInMillis <= System.currentTimeMillis()) {
                Log.e(TAG, "Invalid duration")
                return
            }

            val intent = Intent(context, TaskReceiver::class.java).apply {
                putExtra("title", task.title)
                putExtra("description", task.description)
                putExtra("id", task.id)
            }
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    task.hashCode(),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                durationInMillis,
                pendingIntent
            )
        }
    }

    fun cancelTask(task: Task) {
        val intent = Intent(context, TaskReceiver::class.java).apply {
            putExtra("title", task.title)
            putExtra("description", task.description)
            putExtra("id", task.id)
        }
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                task.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        const val TAG = "TaskScheduler"
    }
}