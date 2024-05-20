package com.benki.taskmanager.utils

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.benki.taskmanager.MainActivity
import com.benki.taskmanager.R

object NotificationUtils {
    fun sendNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int,
        taskId: Long
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("id", taskId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(
                notificationId, NotificationCompat.Builder(context, "task_manager_notifications")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()
            )
        }
    }
}