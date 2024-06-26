package com.benki.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benki.taskmanager.presentation.MainViewModel
import com.benki.taskmanager.presentation.TaskManagerApp
import com.benki.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.loading.value
        }
        setContent {
            TaskManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val startDestination by mainViewModel.startDestination.collectAsStateWithLifecycle()
                    val modalVisible by mainViewModel.modalVisible.collectAsStateWithLifecycle()
                    val taskId = intent.getLongExtra("id", 0L)
                    val unReadNotificationCount by mainViewModel.unReadNotifications.collectAsStateWithLifecycle()
                    TaskManagerApp(
                        startDestination = startDestination,
                        updateOnBoardingVisited = mainViewModel::updateOnBoardingVisited,
                        modalVisible = modalVisible,
                        toggleModal = mainViewModel::toggleModal,
                        taskId = if (taskId > 0L) taskId else null,
                        unReadNotificationCount = unReadNotificationCount
                    )
                }
            }
        }
    }
}


