package com.upf464.koonsdiary.presentation.ui.account.signup

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.upf464.koonsdiary.presentation.ui.account.signup.components.UserImageListItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val pageState = viewModel.pageFlow.collectAsState()
    val firstFieldState = viewModel.firstFieldFlow.collectAsState()
    val firstValidationState = viewModel.firstValidationFlow.collectAsState()
    val secondFieldState = viewModel.secondFieldFlow.collectAsState()
    val secondValidationState = viewModel.secondValidationFlow.collectAsState()
    val imageListState = viewModel.imageListFlow.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                SignUpViewModel.SignUpEvent.NetworkDisconnected ->
                    Toast.makeText(context, "네트워크 오류", Toast.LENGTH_LONG).show()
                SignUpViewModel.SignUpEvent.NoImageSelected ->
                    Toast.makeText(context, "이미지 미선택 오류", Toast.LENGTH_LONG).show()
                SignUpViewModel.SignUpEvent.Success ->
                    Toast.makeText(context, "회원가입 성공", Toast.LENGTH_LONG).show()
                SignUpViewModel.SignUpEvent.UnknownError ->
                    Toast.makeText(context, "오류", Toast.LENGTH_LONG).show()
            }
        }
    }

    Column {
        Text(text = pageState.value.name)

        if (pageState.value == SignUpViewModel.SignUpPage.IMAGE) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier.weight(1f)
            ) {
                items(imageListState.value.size) { index ->
                    UserImageListItem(imageModel = imageListState.value[index]) {
                        viewModel.selectImageAt(index)
                    }
                }
            }
        } else {
            val isPassword = pageState.value == SignUpViewModel.SignUpPage.PASSWORD

            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = firstFieldState.value,
                onValueChange = { value -> viewModel.firstFieldFlow.value = value },
                visualTransformation = if (isPassword) PasswordVisualTransformation()
                else VisualTransformation.None
            )
            Text(text = firstValidationState.value.name)

            if (isPassword) {
                TextField(
                    value = secondFieldState.value,
                    onValueChange = { value -> viewModel.secondFieldFlow.value = value },
                    visualTransformation = PasswordVisualTransformation()
                )
                Text(text = secondValidationState.value.name)
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { viewModel.prevPage() }) {
                Text(text = "이전")
            }
            Button(onClick = { viewModel.nextPage() }) {
                Text(text = "다음")
            }
        }
    }
}
