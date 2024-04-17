package com.benki.taskmanager.data.convertors

import androidx.room.TypeConverter
import com.benki.taskmanager.data.constants.TaskPriority

class PriorityConvertor {
    @TypeConverter
    fun fromPriority(priority: TaskPriority): String = priority.priorityText

    @TypeConverter
    fun toPriority(priority: String): TaskPriority = TaskPriority.valueOf(priority)
}