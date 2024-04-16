package com.benki.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.benki.taskmanager.data.constants.NavigationRoutes
import com.benki.taskmanager.presentation.home.HomeScreen
import com.benki.taskmanager.presentation.onboarding.OnBoardingScreen

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
                navHostController.navigate(NavigationRoutes.HOME)
            })
        }
        composable(route = NavigationRoutes.HOME) {
            HomeScreen()
        }
    }
}