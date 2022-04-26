package com.upf464.koonsdiary.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upf464.koonsdiary.presentation.ui.main.calendar.CalendarScreen
import com.upf464.koonsdiary.presentation.ui.main.components.BottomNavigationBar
import com.upf464.koonsdiary.presentation.ui.main.components.MainNavigationItem
import com.upf464.koonsdiary.presentation.ui.theme.KoonsDiaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoonsDiaryTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                itemList = listOf(
                                    MainNavigationItem(
                                        name = "Calendar",
                                        icon = Icons.Default.Home,
                                        route = MainScreen.CALENDAR.route
                                    ),
                                    MainNavigationItem(
                                        name = "Share",
                                        icon = Icons.Default.Share,
                                        route = MainScreen.SHARE.route
                                    ),
                                    MainNavigationItem(
                                        name = "Cotton",
                                        icon = Icons.Default.Star,
                                        route = MainScreen.COTTON.route
                                    ),
                                    MainNavigationItem(
                                        name = "Report",
                                        icon = Icons.Default.Edit,
                                        route = MainScreen.REPORT.route
                                    ),
                                    MainNavigationItem(
                                        name = "Notification",
                                        icon = Icons.Default.Notifications,
                                        route = MainScreen.NOTIFICATION.route
                                    ),
                                ),
                                navController = navController,
                                onItemClick = { item ->
                                    navController.navigate(item.route)
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                            Navigation(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.CALENDAR.route
    ) {
        composable(route = MainScreen.CALENDAR.route) {
            CalendarScreen()
        }
        composable(route = MainScreen.SHARE.route) { }
        composable(route = MainScreen.COTTON.route) { }
        composable(route = MainScreen.REPORT.route) { }
        composable(route = MainScreen.NOTIFICATION.route) { }
    }
}
