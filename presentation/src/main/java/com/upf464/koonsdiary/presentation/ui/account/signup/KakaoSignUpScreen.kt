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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.upf464.koonsdiary.presentation.model.account.SignUpPage
import com.upf464.koonsdiary.presentation.ui.account.signup.components.UserImageListItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun KakaoSignUpScreen(
    viewModel: KakaoSignUpViewModel = hiltViewModel()
) {
    val pageState = viewModel.pageFlow.collectAsState()
    val fieldState = viewModel.fieldFlow.collectAsState()
    val validationState = viewModel.validationFlow.collectAsState()
    val imageListState = viewModel.imageListFlow.collectAsState()
    val context = LocalContext.current
    val state = viewModel.stateFlow.collectAsState()

    when (state.value) {
        SignUpState.NoNetwork ->
            Toast.makeText(context, "네트워크 오류", Toast.LENGTH_LONG).show()
        SignUpState.Success ->
            Toast.makeText(context, "회원가입 성공", Toast.LENGTH_LONG).show()
        SignUpState.Failure ->
            Toast.makeText(context, "오류", Toast.LENGTH_LONG).show()
        SignUpState.None -> {}
    }

    Column {
        Text(text = pageState.value.name)

        if (pageState.value == SignUpPage.IMAGE) {
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
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = fieldState.value,
                onValueChange = { value -> viewModel.fieldFlow.value = value }
            )
            Text(text = validationState.value.toString())
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
