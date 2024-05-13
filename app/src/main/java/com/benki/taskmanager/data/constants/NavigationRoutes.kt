package com.benki.taskmanager.data.constants

object NavigationRoutes {
    const val ON_BOARDING_ROUTE = "on_boarding"
    const val HOME = "home"
    const val NOTIFICATIONS = "notifications"
    const val PROFILE = "profile"
    const val REPORT = "report"
    const val CREATE_TASK = "create_task"
    const val CREATE_PROJECT = "create_project"
    const val TASKS = "tasks"
    const val ALL_TASKS = "all_tasks"
    const val DETAIL_TASK = "detail_task"
    val IGNORE_BOTTOM_BAR = listOf(ON_BOARDING_ROUTE, CREATE_TASK, CREATE_PROJECT, TASKS, ALL_TASKS)
}