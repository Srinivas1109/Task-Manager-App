package com.benki.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.benki.taskmanager.data.constants.NavigationRoutes
import com.benki.taskmanager.data.constants.NavigationRoutes.CREATE_PROJECT
import com.benki.taskmanager.data.constants.NavigationRoutes.HOME
import com.benki.taskmanager.presentation.createproject.CreateProjectScreen
import com.benki.taskmanager.presentation.createtask.CreateTaskScreen
import com.benki.taskmanager.presentation.home.HomeScreen
import com.benki.taskmanager.presentation.notifications.NotificationScreen
import com.benki.taskmanager.presentation.onboarding.OnBoardingScreen
import com.benki.taskmanager.presentation.profile.ProfileScreen
import com.benki.taskmanager.presentation.report.ReportScreen

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: String = NavigationRoutes.ON_BOARDING_ROUTE,
    updateOnBoardingVisited: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(route = NavigationRoutes.ON_BOARDING_ROUTE) {
            OnBoardingScreen(navigateToHome = {
                updateOnBoardingVisited()
                navHostController.navigate(HOME)
            })
        }
        composable(route = HOME) {
            HomeScreen()
        }
        composable(route = NavigationRoutes.NOTIFICATIONS) {
            NotificationScreen()
        }
        composable(route = NavigationRoutes.REPORT) {
            ReportScreen()
        }
        composable(route = NavigationRoutes.PROFILE) {
            ProfileScreen()
        }
        composable(route = NavigationRoutes.CREATE_TASK) {
            CreateTaskScreen(onBackButtonClick = { navHostController.popBackStack() },
                onTaskCreate = {
                    navHostController.navigate(HOME) {
                        popUpTo(HOME) {
                            inclusive = true
                        }
                    }
                }, onClickCreateProject = { navHostController.navigate(CREATE_PROJECT) })
        }
        composable(route = CREATE_PROJECT) {
            CreateProjectScreen(
                onBackButtonClick = { navHostController.popBackStack() },
                onProjectCreate = {
                    navHostController.navigate(HOME) {
                        popUpTo(HOME) {
                            inclusive = true
                        }
                    }
                })
        }
    }
}