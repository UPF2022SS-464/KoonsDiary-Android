package com.upf464.koonsdiary.presentation.ui.account.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.upf464.koonsdiary.presentation.ui.account.SignInScreen

@Composable
internal fun SignInHomeScreen(
    navController: NavController,
    viewModel: SignInHomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                SignInHomeEvent.NavigateToEmailSignIn ->
                    navController.navigate(SignInScreen.SIGN_IN_EMAIL.route)
                SignInHomeEvent.NavigateToEmailSignUp ->
                    navController.navigate(SignInScreen.SIGN_UP_EMAIL.route)
                SignInHomeEvent.Success ->
                    Toast.makeText(context, "로그인 성공", Toast.LENGTH_LONG).show()
                SignInHomeEvent.NavigateToKakaoSignUp ->
                    navController.navigate(SignInScreen.SIGN_UP_KAKAO.route)
                SignInHomeEvent.UnknownError ->
                    Toast.makeText(context, "에러 발생", Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = { viewModel.signInWithEmail() }) {
            Text(text = "이메일 로그인",)
        }
        Button(onClick = { viewModel.signInWithKakao() }) {
            Text(text = "카카오 로그인",)
        }
        Button(onClick = { viewModel.signUpWithEmail() }) {
            Text(text = "회원가입",)
        }
    }
}
