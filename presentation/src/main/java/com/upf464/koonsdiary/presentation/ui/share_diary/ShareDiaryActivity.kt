package com.upf464.koonsdiary.presentation.ui.share_diary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.ui.share_diary.diary.ShareDiaryDetailScreen
import com.upf464.koonsdiary.presentation.ui.share_diary.editor.ShareEditorScreen
import com.upf464.koonsdiary.presentation.ui.theme.KoonsDiaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareDiaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoonsDiaryTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = intent.getStringExtra(Constants.EXTRA_SHARE_DIARY_ROUTE) ?: ""
                    ) {
                        composable(
                            route = ShareDiaryNavigation.DIARY_DETAIL.route,
                            arguments = listOf(
                                navArgument(Constants.PARAM_DIARY_ID) {
                                    type = NavType.IntType
                                    defaultValue = intent.getIntExtra(Constants.PARAM_DIARY_ID, 0)
                                }
                            )
                        ) {
                            ShareDiaryDetailScreen(navController = navController)
                        }
                        composable(
                            route = ShareDiaryNavigation.EDITOR.route,
                            arguments = listOf(
                                navArgument(Constants.PARAM_GROUP_ID) {
                                    type = NavType.StringType
                                    defaultValue = intent.getIntExtra(Constants.PARAM_GROUP_ID, 0)
                                }
                            )
                        ) {
                            ShareEditorScreen(navController = navController)
                        }
                        composable(
                            route = ShareDiaryNavigation.EDITOR.route + "/{${Constants.PARAM_GROUP_ID}}/{${Constants.PARAM_DIARY_ID}}"
                        ) {
                            ShareEditorScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
