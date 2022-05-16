package com.upf464.koonsdiary.presentation.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.ui.account.signin.EmailSignInScreen
import com.upf464.koonsdiary.presentation.ui.account.signin.SignInHomeScreen
import com.upf464.koonsdiary.presentation.ui.account.signup.SignUpScreen
import com.upf464.koonsdiary.presentation.ui.main.MainActivity
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
                        startDestination = SignInNavigation.SIGN_IN_HOME.route
                    ) {
                        composable(route = SignInNavigation.SIGN_IN_HOME.route) {
                            SignInHomeScreen(navController = navController)
                        }
                        composable(route = SignInNavigation.SIGN_IN_EMAIL.route) {
                            EmailSignInScreen(navController = navController)
                        }
                        composable(route = SignInNavigation.SIGN_UP.route + "/{${Constants.PARAM_SIGN_UP_TYPE}}") {
                            SignUpScreen()
                        }
                    }
                }
            }
        }
    }

    fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
