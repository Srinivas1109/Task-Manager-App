package com.benki.taskmanager.data.convertors

import androidx.room.TypeConverter
import com.benki.taskmanager.data.constants.TaskPriority
import com.benki.taskmanager.data.constants.TaskStatus

class StatusConvertor {
    @TypeConverter
    fun fromPriority(status: TaskStatus): String = status.statusText

    @TypeConverter
    fun toPriority(status: String): TaskStatus = TaskStatus.valueOf(status)
}