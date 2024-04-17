package com.benki.taskmanager.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StackedBarChart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.benki.taskmanager.data.constants.NavigationRoutes.HOME
import com.benki.taskmanager.data.constants.NavigationRoutes.NOTIFICATIONS
import com.benki.taskmanager.data.constants.NavigationRoutes.PROFILE
import com.benki.taskmanager.data.constants.NavigationRoutes.REPORT

@Composable
fun TaskManagerBottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    modalVisible: Boolean = false,
    toggleModal: (Boolean) -> Unit,
) {
    val destination by navController.currentBackStackEntryAsState()
    val activeScreen = destination?.destination?.route ?: ""

    BottomAppBar(
        actions = {
            NavigationBarItem(
                selected = activeScreen == HOME,
                onClick = {
                    if (modalVisible) {
                        toggleModal(false)
                    }
                    navController.navigate(HOME)
                },
                icon = {
                    Icon(
                        imageVector = if (activeScreen == HOME) Icons.Filled.Home else Icons.Outlined.Home,
                        contentDescription = null
                    )
                },
            )

            NavigationBarItem(
                selected = activeScreen == NOTIFICATIONS,
                onClick = {
                    if (modalVisible) {
                        toggleModal(false)
                    }
                    navController.navigate(NOTIFICATIONS)
                },
                icon = {
                    Icon(
                        imageVector = if (activeScreen == NOTIFICATIONS) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                        contentDescription = null
                    )
                })

            Box(
                modifier = Modifier.size(60.dp),
            ) {
                IconButton(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    onClick = {
                        if (modalVisible) {
                            toggleModal(false)
                        } else {
                            toggleModal(true)
                        }
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        containerColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Task, contentDescription = null)
                }
            }

            NavigationBarItem(
                selected = activeScreen == REPORT,
                onClick = {
                    if (modalVisible) {
                        toggleModal(false)
                    }
                    navController.navigate(REPORT)
                },
                icon = {
                    Icon(
                        imageVector = if (activeScreen == REPORT) Icons.Filled.StackedBarChart else Icons.Outlined.StackedBarChart,
                        contentDescription = null
                    )
                })

            NavigationBarItem(
                selected = activeScreen == PROFILE,
                onClick = {
                    if (modalVisible) {
                        toggleModal(false)
                    }
                    navController.navigate(PROFILE)
                },
                icon = {
                    Icon(
                        imageVector = if (activeScreen == PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                        contentDescription = null
                    )
                })
        },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    )
}