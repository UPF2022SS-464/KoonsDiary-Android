package com.upf464.koonsdiary.presentation.ui.share_diary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.ui.share_diary.diary.ShareDiaryDetailScreen
import com.upf464.koonsdiary.presentation.ui.theme.KoonsDiaryTheme

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
                        composable(ShareDiaryNavigation.DiaryDetail.route + "/{${Constants.PARAM_DIARY_ID}}") {
                            ShareDiaryDetailScreen()
                        }
                    }
                }
            }
        }
    }
}
