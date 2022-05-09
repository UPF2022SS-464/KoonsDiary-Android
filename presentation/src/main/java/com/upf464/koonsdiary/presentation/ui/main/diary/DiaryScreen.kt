package com.upf464.koonsdiary.presentation.ui.main.diary

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.ui.main.diary.add.AddDiaryScreen
import com.upf464.koonsdiary.presentation.ui.main.diary.calendar.CalendarScreen
import com.upf464.koonsdiary.presentation.ui.main.diary.detail.DiaryDetailScreen

@Composable
fun DiaryScreen() {
    val navController = rememberNavController()
    Navigation(navController = navController)
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = DiaryNavigation.ADD.route
    ) {
        composable(route = DiaryNavigation.CALENDAR.route) {
            CalendarScreen(navController = navController)
        }
        composable(route = DiaryNavigation.DETAIL.route + "/{${Constants.PARAM_DIARY_ID}}") {
            DiaryDetailScreen()
        }
        composable(route = DiaryNavigation.ADD.route) {
            AddDiaryScreen()
        }
    }
}
