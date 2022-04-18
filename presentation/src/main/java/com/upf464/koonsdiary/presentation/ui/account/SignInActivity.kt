package com.upf464.koonsdiary.presentation.ui.account

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upf464.koonsdiary.presentation.ui.account.signin.SignInHomeScreen
import com.upf464.koonsdiary.presentation.ui.account.signup.KakaoSignUpScreen
import com.upf464.koonsdiary.presentation.ui.theme.KoonsDiaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoonsDiaryTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = SignInScreen.SIGN_IN_HOME.route
                    ) {
                        composable(route = SignInScreen.SIGN_IN_HOME.route) {
                            SignInHomeScreen(navController = navController)
                        }
                        composable(route = SignInScreen.SIGN_IN_EMAIL.route) {

                        }
                        composable(route = SignInScreen.SIGN_UP_KAKAO.route) {
                            KakaoSignUpScreen()
                        }
                        composable(route = SignInScreen.SIGN_UP_EMAIL.route) {

                        }
                    }
                }
            }
        }
    }
}
