package com.benki.taskmanager.data.constants

import androidx.compose.ui.graphics.Color

enum class TaskStatus(val statusText: String, val color: Color) {
    NOT_STARTED("Not Started", Color(0xFF808080)),
    IN_PROGRESS("In Progress", Color(0xFF007bff)),
    ON_HOLD("On Hold", Color(0xFFffc107)),
    COMPLETED("Completed", Color(0xFF28a745)),
    CANCELLED("Cancelled", Color(0xFFdc3545)),
    DEFERRED("Deferred", Color(0xFF17a2b8)),
    WAITING_FOR_APPROVAL("Waiting For Approval", Color(0xFF6610f2)),
    NEEDS_REVIEW("Needs Review", Color(0xFFffc107)),
    BLOCKED("Blocked", Color(0xFFdc3545)),
    OVERDUE("Overdue", Color(0xFFdc3545)),
    REOPENED("Reopened", Color(0xFFffc107)),
    IN_REVIEW("In Review", Color(0xFF6610f2)),
    PENDING("Pending", Color(0xFFffc107))
}