package com.benki.taskmanager.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.benki.taskmanager.data.constants.NavigationRoutes.CREATE_PROJECT
import com.benki.taskmanager.data.constants.NavigationRoutes.CREATE_TASK
import com.benki.taskmanager.data.constants.NavigationRoutes.DETAIL_TASK
import com.benki.taskmanager.data.constants.NavigationRoutes.HOME
import com.benki.taskmanager.data.constants.NavigationRoutes.IGNORE_BOTTOM_BAR
import com.benki.taskmanager.navigation.SetupNavGraph
import com.benki.taskmanager.presentation.components.TaskManagerBottomBar

@Composable

fun TaskManagerApp(
    startDestination: String,
    modalVisible: Boolean = false,
    taskId: Long? = null,
    unReadNotificationCount: Int = 0,
    toggleModal: (Boolean) -> Unit,
    updateOnBoardingVisited: () -> Unit
) {
    val context = LocalContext.current
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }

    val notificationLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermission = isGranted
        }

    val navController = rememberNavController()
    Scaffold(bottomBar = {
        val destination by navController.currentBackStackEntryAsState()
        val activeScreen = destination?.destination?.route ?: ""
        AnimatedVisibility(
            visible = !IGNORE_BOTTOM_BAR.contains(activeScreen) && !activeScreen.contains(
                DETAIL_TASK
            ),
            enter = slideIn {
                IntOffset(0, it.height)
            },
            exit = slideOut {
                IntOffset(0, it.height - 10)
            }
        ) {
            TaskManagerBottomBar(
                navController = navController,
                toggleModal = toggleModal,
                modalVisible = modalVisible,
                activeScreen = activeScreen,
                unReadNotificationCount = unReadNotificationCount
            )
        }
    }) { contentPadding ->
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SetupNavGraph(
                    navHostController = navController,
                    startDestination = startDestination,
                    updateOnBoardingVisited = updateOnBoardingVisited
                )
                AnimatedVisibility(
                    visible = modalVisible,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .padding(contentPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            toggleModal(false)
                                            navController.navigate(CREATE_TASK) {
                                                popUpTo(HOME) {
                                                    inclusive = false
                                                }
                                            }
                                        },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Create New Task",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(3.dp)
                                        .border(3.dp, MaterialTheme.colorScheme.primaryContainer)
                                )
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            toggleModal(false)
                                            navController.navigate(CREATE_PROJECT) {
                                                popUpTo(HOME) {
                                                    inclusive = false
                                                }
                                            }
                                        },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Create New Project",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(3.dp)
                                        .border(3.dp, MaterialTheme.colorScheme.primaryContainer)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LaunchedEffect(key1 = taskId) {
        if(taskId != null){
            navController.navigate("$DETAIL_TASK/$taskId"){
                popUpTo(HOME){
                    inclusive = true
                }
            }
        }
    }
}