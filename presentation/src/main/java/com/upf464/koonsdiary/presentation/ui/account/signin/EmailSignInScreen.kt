package com.upf464.koonsdiary.presentation.ui.account.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun EmailSignInScreen(
    viewModel: EmailSignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                SignInEvent.Invalid -> Toast.makeText(context, "로그인 정보 오류", Toast.LENGTH_LONG).show()
                SignInEvent.Success -> Toast.makeText(context, "로그인 성공", Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = viewModel.usernameFlow.collectAsState().value,
            onValueChange = { viewModel.usernameFlow.value = it }
        )
        TextField(
            value = viewModel.passwordFlow.collectAsState().value,
            onValueChange = { viewModel.passwordFlow.value = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = { viewModel.signIn() }) {
            Text(text = "로그인")
        }
    }
}
