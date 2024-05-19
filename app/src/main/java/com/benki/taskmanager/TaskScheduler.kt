package com.benki.taskmanager

import android.app.AlarmManager
import android.content.Context

class TaskScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    fun scheduleTask() {
        alarmManager
    }
}