package com.benki.taskmanager.data.constants

import androidx.compose.ui.graphics.Color

enum class TaskPriority(val priorityText: String, val color: Color) {
    LOW("Low", Color(0xFF28a745)), // Green color
    MEDIUM("Medium", Color(0xFF17a2b8)), // Blue color
    HIGH("High", Color(0xFFffc107)), // Yellow color
    CRITICAL("Critical", Color(0xFFdc3545)), // Red color
    NORMAL("Normal", Color(0xFF6c757d)), // Gray color
}