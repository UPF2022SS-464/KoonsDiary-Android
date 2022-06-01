package com.upf464.koonsdiary.presentation.ui.main.share

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.ui.main.share.add_group.AddGroupScreen
import com.upf464.koonsdiary.presentation.ui.main.share.group.ShareGroupScreen
import com.upf464.koonsdiary.presentation.ui.main.share.group_list.ShareGroupListScreen

@Composable
internal fun ShareScreen() {
    val navController = rememberNavController()
    Navigation(navController = navController)
}

@Composable
private fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ShareNavigation.GROUP_LIST.route
    ) {
        composable(route = ShareNavigation.GROUP_LIST.route) {
            ShareGroupListScreen(
                navController = navController
            )
        }
        composable(route = ShareNavigation.ADD_GROUP.route) {
            AddGroupScreen(
                navController = navController
            )
        }
        composable(route = ShareNavigation.GROUP_DETAIL.route + "/{${Constants.PARAM_GROUP_ID}}") {
            ShareGroupScreen(navController = navController)
        }
    }
}
