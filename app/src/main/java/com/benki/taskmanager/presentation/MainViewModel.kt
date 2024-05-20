package com.benki.taskmanager.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.taskmanager.data.constants.NavigationRoutes
import com.benki.taskmanager.data.repository.TaskNotificationRepository
import com.benki.taskmanager.data.repository.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val taskNotificationRepository: TaskNotificationRepository
) :
    ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _startDestination: MutableStateFlow<String> =
        MutableStateFlow(NavigationRoutes.ON_BOARDING_ROUTE)
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    private val _modalVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val modalVisible: StateFlow<Boolean> = _modalVisible.asStateFlow()

    val unReadNotifications = taskNotificationRepository.getUnReadTaskNotificationsCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }
            userPreferencesRepository.visitedOnBoarding.collect { visited ->
                _startDestination.update { if (visited) NavigationRoutes.HOME else NavigationRoutes.ON_BOARDING_ROUTE }
                _loading.update { false }
            }

        }
    }

    fun updateOnBoardingVisited() {
        viewModelScope.launch {
            userPreferencesRepository.updateOnBoardingVisit(true)
        }
    }

    fun toggleModal(visible: Boolean) {
        _modalVisible.update { visible }
    }

}