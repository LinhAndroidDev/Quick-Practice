package com.example.quickpractice.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quickpractice.ui.theme.screen.exam.ExamScreen
import com.example.quickpractice.ui.theme.screen.exam_history.ExamHistoryScreen
import com.example.quickpractice.ui.theme.screen.exam_list.ExamListScreen
import com.example.quickpractice.ui.theme.screen.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HOME.route
    ) {
        composable(Route.HOME.route) { HomeScreen(navController) }
        composable(Route.EXAM_LIST.route) { ExamListScreen(navController) }
        composable(Route.EXAM.route) { ExamScreen(navController) }
        composable(Route.EXAM_HISTORY.route) { ExamHistoryScreen(navController) }
    }
}