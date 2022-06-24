package com.upf464.koonsdiary.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.main.components.BottomNavigationBar
import com.upf464.koonsdiary.presentation.ui.main.components.MainNavigationItem
import com.upf464.koonsdiary.presentation.ui.main.diary.DiaryScreen
import com.upf464.koonsdiary.presentation.ui.main.notification.NotificationScreen
import com.upf464.koonsdiary.presentation.ui.main.share.ShareScreen
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
                    val backStackState = navController.currentBackStackEntryAsState()
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                itemList = listOf(
                                    MainNavigationItem(
                                        name = stringResource(id = R.string.bottomBar_diary),
                                        icon = painterResource(id = R.drawable.ic_calendar),
                                        route = MainNavigation.DIARY.route
                                    ),
                                    MainNavigationItem(
                                        name = stringResource(id = R.string.bottomBar_share),
                                        icon = painterResource(id = R.drawable.ic_share_diary),
                                        route = MainNavigation.SHARE.route
                                    ),
                                    MainNavigationItem(
                                        name = stringResource(id = R.string.bottomBar_cotton),
                                        icon = painterResource(id = R.drawable.ic_cotton),
                                        route = MainNavigation.COTTON.route
                                    ),
                                    MainNavigationItem(
                                        name = stringResource(id = R.string.bottomBar_report),
                                        icon = painterResource(id = R.drawable.ic_statistics),
                                        route = MainNavigation.REPORT.route
                                    ),
                                    MainNavigationItem(
                                        name = stringResource(id = R.string.bottomBar_notification),
                                        icon = painterResource(id = R.drawable.ic_notification),
                                        route = MainNavigation.NOTIFICATION.route
                                    ),
                                ),
                                onItemClick = { item ->
                                    if (item.route != navController.currentDestination?.route) {
                                        navController.navigate(item.route)
                                    }
                                },
                                backStackState = backStackState
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
        composable(route = MainNavigation.SHARE.route) {
            ShareScreen()
        }
        composable(route = MainNavigation.COTTON.route) { }
        composable(route = MainNavigation.REPORT.route) { }
        composable(route = MainNavigation.NOTIFICATION.route) {
            NotificationScreen()
        }
    }
}
