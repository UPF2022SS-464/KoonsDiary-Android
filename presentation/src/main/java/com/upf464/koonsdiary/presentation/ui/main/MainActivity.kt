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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.main.components.BottomNavigationBar
import com.upf464.koonsdiary.presentation.ui.main.components.MainNavigationItem
import com.upf464.koonsdiary.presentation.ui.main.diary.DiaryScreen
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
                                        name = stringResource(id = R.string.bottomBar_diary),
                                        icon = Icons.Default.Home,
                                        route = MainNavigation.DIARY.route
                                    ),
                                    MainNavigationItem(
                                        name = stringResource(id = R.string.bottomBar_share),
                                        icon = Icons.Default.Share,
                                        route = MainNavigation.SHARE.route
                                    ),
                                    MainNavigationItem(
                                        name = stringResource(id = R.string.bottomBar_cotton),
                                        icon = Icons.Default.Star,
                                        route = MainNavigation.COTTON.route
                                    ),
                                    MainNavigationItem(
                                        name = stringResource(id = R.string.bottomBar_report),
                                        icon = Icons.Default.Edit,
                                        route = MainNavigation.REPORT.route
                                    ),
                                    MainNavigationItem(
                                        name = stringResource(id = R.string.bottomBar_notification),
                                        icon = Icons.Default.Notifications,
                                        route = MainNavigation.NOTIFICATION.route
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
        startDestination = MainNavigation.DIARY.route
    ) {
        composable(route = MainNavigation.DIARY.route) {
            DiaryScreen()
        }
        composable(route = MainNavigation.SHARE.route) { }
        composable(route = MainNavigation.COTTON.route) { }
        composable(route = MainNavigation.REPORT.route) { }
        composable(route = MainNavigation.NOTIFICATION.route) { }
    }
}
