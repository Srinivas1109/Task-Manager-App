package com.benki.taskmanager.presentation.all_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.taskmanager.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AllTasksViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {
    val tasks = repository.getTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}