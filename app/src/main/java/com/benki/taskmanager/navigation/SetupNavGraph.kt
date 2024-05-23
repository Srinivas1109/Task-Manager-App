package com.benki.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.benki.taskmanager.data.constants.NavigationRoutes
import com.benki.taskmanager.data.constants.NavigationRoutes.ALL_TASKS
import com.benki.taskmanager.data.constants.NavigationRoutes.CREATE_PROJECT
import com.benki.taskmanager.data.constants.NavigationRoutes.DETAIL_TASK
import com.benki.taskmanager.data.constants.NavigationRoutes.HOME
import com.benki.taskmanager.data.constants.NavigationRoutes.TASKS
import com.benki.taskmanager.presentation.task_details.DetailScreen
import com.benki.taskmanager.presentation.all_tasks.AllTasks
import com.benki.taskmanager.presentation.createproject.CreateProjectScreen
import com.benki.taskmanager.presentation.createtask.CreateTaskScreen
import com.benki.taskmanager.presentation.home.HomeScreen
import com.benki.taskmanager.presentation.notifications.NotificationScreen
import com.benki.taskmanager.presentation.onboarding.OnBoardingScreen
import com.benki.taskmanager.presentation.info.InfoScreen
import com.benki.taskmanager.presentation.report.ReportScreen
import com.benki.taskmanager.presentation.tasks.Tasks

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
            HomeScreen(
                navigateToTasks = { navHostController.navigate("$TASKS/${it.name}") },
                navigateToAllTasks = { navHostController.navigate(ALL_TASKS) })
        }
        composable(route = NavigationRoutes.NOTIFICATIONS) {
            NotificationScreen(
                navigateToDetail = { taskId -> navHostController.navigate("$DETAIL_TASK/${taskId}") }
            )
        }
        composable(route = NavigationRoutes.REPORT) {
            ReportScreen()
        }
        composable(route = NavigationRoutes.INFO) {
            InfoScreen()
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

        composable(route = "$TASKS/{status}", arguments = listOf(navArgument(name = "status") {
            type = NavType.StringType
        })) {
            val status = it.arguments?.getString("status")
            Tasks(
                status = status!!,
                navigateBack = { navHostController.popBackStack() },
                navigateToDetail = { taskId -> navHostController.navigate("$DETAIL_TASK/${taskId}") })
        }

        composable(route = ALL_TASKS) {
            AllTasks(
                navigateBack = { navHostController.popBackStack() },
                navigateToTask = { taskId -> navHostController.navigate("$DETAIL_TASK/${taskId}") })
        }

        composable(
            route = "$DETAIL_TASK/{taskId}",
            arguments = listOf(navArgument(name = "taskId") {
                type = NavType.LongType
            })
        ) {
            val taskId = it.arguments?.getLong("taskId")
            taskId?.let { id ->
                DetailScreen(
                    taskId = id,
                    navigateBack = { navHostController.popBackStack() })
            }
        }
    }
}