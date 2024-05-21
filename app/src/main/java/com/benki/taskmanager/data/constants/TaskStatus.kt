package com.benki.taskmanager.data.constants

import androidx.compose.ui.graphics.Color

enum class TaskStatus(val statusText: String, val color: Color) {
    NOT_STARTED("Not Started", Color(0xFF808080)),
    IN_PROGRESS("In Progress", Color(0xFF007bff)),
    COMPLETED("Completed", Color(0xFF28a745)),
    CANCELLED("Cancelled", Color(0xFFdc3545)),
    NEEDS_REVIEW("Needs Review", Color(0xFFFF9F66)),
    PENDING("Pending", Color(0xFFffc107))
}