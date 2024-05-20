package com.benki.taskmanager.presentation.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.taskmanager.data.model.TaskNotification
import com.benki.taskmanager.data.repository.TaskNotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val notificationRepository: TaskNotificationRepository) :
    ViewModel() {
    val unreadNotifications = notificationRepository.getUnReadTaskNotifications()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun deleteNotification(taskNotification: TaskNotification) {
        viewModelScope.launch {
            notificationRepository.deleteTaskNotification(taskNotification)
        }
    }
}