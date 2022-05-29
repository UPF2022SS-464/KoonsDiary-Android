package com.upf464.koonsdiary.presentation.ui.account.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.ui.account.SignInActivity
import com.upf464.koonsdiary.presentation.ui.account.components.AccountTextField
import com.upf464.koonsdiary.presentation.ui.account.signup.components.UserImageListItem
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                SignUpEvent.NoNetwork -> {
                    // TODO()
                }
                SignUpEvent.Success -> {
                    (context as? SignInActivity)?.startMain()
                }
                SignUpEvent.UnknownError -> {
                    // TODO()
                }
            }
        }
    }

    SignUpScreen(
        type = viewModel.signUpType,
        page = viewModel.pageFlow.collectAsState().value,
        firstFieldValue = viewModel.firstFieldFlow.collectAsState().value,
        onFirstFieldChanged = { viewModel.firstFieldFlow.value = it },
        firstValidationState = viewModel.firstValidationFlow.collectAsState().value,
        secondFieldValue = viewModel.secondFieldFlow.collectAsState().value,
        onSecondFieldChanged = { viewModel.secondFieldFlow.value = it },
        secondValidationState = viewModel.secondValidationFlow.collectAsState().value,
        imageModelList = viewModel.imageListFlow.collectAsState().value,
        onSelectImageAt = { viewModel.selectImageAt(it) },
        selectedImage = viewModel.imageFlow.collectAsState().value ?: User.Image(),
        onNextClicked = { viewModel.nextPage() },
        onPrevClicked = { viewModel.prevPage() }
    )
}

@Composable
private fun SignUpScreen(
    type: SignUpType = SignUpType.EMAIL,
    page: SignUpPage = SignUpPage.USERNAME,
    firstFieldValue: String = "",
    onFirstFieldChanged: (String) -> Unit = {},
    firstValidationState: SignUpValidationState = SignUpValidationState.Waiting,
    secondFieldValue: String = "",
    onSecondFieldChanged: (String) -> Unit = {},
    secondValidationState: SignUpValidationState = SignUpValidationState.Waiting,
    imageModelList: List<User.Image> = emptyList(),
    onSelectImageAt: (Int) -> Unit = {},
    selectedImage: User.Image = User.Image(),
    onNextClicked: () -> Unit = {},
    onPrevClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp, bottom = 32.dp, start = 32.dp, end = 32.dp)
    ) {
        Text(
            text = when (page) {
                SignUpPage.EMAIL -> "이메일 주소를 입력해주세요"
                SignUpPage.USERNAME -> "아이디를 입력해주세요"
                SignUpPage.PASSWORD -> "비밀번호를 입력해주세요"
                SignUpPage.IMAGE -> "프로필을 설정해주세요"
                SignUpPage.NICKNAME -> "닉네임을 설정해주세요"
            },
            color = KoonsColor.Black100,
            style = KoonsTypography.H3
        )

        when (page) {
            SignUpPage.EMAIL -> EmailField(
                value = firstFieldValue,
                onValueChanged = onFirstFieldChanged,
                validationState = firstValidationState
            )
            SignUpPage.USERNAME -> UsernameField(
                value = firstFieldValue,
                onValueChanged = onFirstFieldChanged,
                validationState = firstValidationState
            )
            SignUpPage.PASSWORD -> PasswordField(
                passwordValue = firstFieldValue,
                onPasswordChanged = onFirstFieldChanged,
                passwordValidationState = firstValidationState,
                confirmValue = secondFieldValue,
                onConfirmChanged = onSecondFieldChanged,
                confirmValidationState = secondValidationState,
            )
            SignUpPage.IMAGE -> ImageField(
                imageModelList = imageModelList,
                onSelectImageAt = onSelectImageAt
            )
            SignUpPage.NICKNAME -> NicknameField(
                imageModel = selectedImage,
                value = firstFieldValue,
                onValueChanged = onFirstFieldChanged,
                validationState = firstValidationState
            )
        }

        if (page != SignUpPage.IMAGE) {
            Spacer(modifier = Modifier.weight(1f))
        }

        PageButtons(
            onPrevClicked = onPrevClicked,
            onNextClicked = onNextClicked,
            isFirstPage = when (type) {
                SignUpType.KAKAO -> page == SignUpPage.USERNAME
                SignUpType.EMAIL -> page == SignUpPage.EMAIL
            },
            isLastPage = page == SignUpPage.NICKNAME,
            showNext = page != SignUpPage.IMAGE
        )
    }
}

@Composable
private fun EmailField(
    value: String,
    onValueChanged: (String) -> Unit,
    validationState: SignUpValidationState
) {
    AccountTextField(
        value = value,
        onValueChanged = onValueChanged,
        placeHolder = "이메일을 입력해주세요",
        paddingTop = 52.dp
    )
    when (validationState) {
        SignUpValidationState.Duplicated -> {
            WarningText(
                text = "이미 가입된 이메일입니다",
                color = KoonsColor.Red
            )
        }
        SignUpValidationState.Invalid -> {
            WarningText(
                text = "올바르지 않은 이메일입니다",
                color = KoonsColor.Red
            )
        }
        SignUpValidationState.Waiting -> {
            WarningText(
                text = "잠시만 기다려주세요",
                color = KoonsColor.Black40
            )
        }
        SignUpValidationState.Success -> {
            WarningText(
                text = "사용 가능한 이메일입니다",
                color = KoonsColor.Green
            )
        }
        else -> {}
    }
}

@Composable
private fun UsernameField(
    value: String,
    onValueChanged: (String) -> Unit,
    validationState: SignUpValidationState
) {
    AccountTextField(
        value = value,
        onValueChanged = onValueChanged,
        placeHolder = "아이디를 입력해주세요",
        paddingTop = 52.dp
    )
    when (validationState) {
        SignUpValidationState.Duplicated -> {
            WarningText(
                text = "이미 존재하는 아이디입니다",
                color = KoonsColor.Red
            )
        }
        SignUpValidationState.Invalid -> {
            WarningText(
                text = "올바르지 않은 아이디입니다",
                color = KoonsColor.Red
            )
        }
        SignUpValidationState.Waiting -> {
            WarningText(
                text = "잠시만 기다려주세요",
                color = KoonsColor.Black40
            )
        }
        SignUpValidationState.Success -> {
            WarningText(
                text = "사용 가능한 아이디입니다",
                color = KoonsColor.Green
            )
        }
        else -> {}
    }
}

@Composable
private fun PasswordField(
    passwordValue: String,
    onPasswordChanged: (String) -> Unit,
    passwordValidationState: SignUpValidationState,
    confirmValue: String,
    onConfirmChanged: (String) -> Unit,
    confirmValidationState: SignUpValidationState,
) {
    AccountTextField(
        value = passwordValue,
        onValueChanged = onPasswordChanged,
        isPassword = true,
        placeHolder = "비밀번호를 입력해주세요 (8자 이상)",
        paddingTop = 52.dp
    )
    AccountTextField(
        value = confirmValue,
        onValueChanged = onConfirmChanged,
        isPassword = true,
        placeHolder = "비밀번호를 한 번 더 입력해주세요",
        paddingTop = 24.dp
    )
    when {
        passwordValidationState == SignUpValidationState.Invalid -> {
            WarningText(
                text = "올바르지 않은 비밀번호입니다",
                color = KoonsColor.Red
            )
        }
        confirmValidationState == SignUpValidationState.Invalid -> {
            WarningText(
                text = "비밀번호가 일치하지 않습니다",
                color = KoonsColor.Red
            )
        }
        else -> {}
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.ImageField(
    imageModelList: List<User.Image>,
    onSelectImageAt: (Int) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(imageModelList.size) { index ->
            UserImageListItem(imageModel = imageModelList[index]) {
                onSelectImageAt(index)
            }
        }
    }
}

@Composable
private fun NicknameField(
    imageModel: User.Image,
    value: String,
    onValueChanged: (String) -> Unit,
    validationState: SignUpValidationState
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageModel.path,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 48.dp)
                .size(150.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }

    AccountTextField(
        value = value,
        onValueChanged = onValueChanged,
        placeHolder = "닉네임을 입력해주세요 (2 ~ 12자)",
        paddingTop = 36.dp
    )

    when (validationState) {
        SignUpValidationState.Invalid -> {
            WarningText(
                text = "올바르지 않은 닉네임입니다",
                color = KoonsColor.Red
            )
        }
        SignUpValidationState.Success -> {
            WarningText(
                text = "사용 가능한 닉네임입니다",
                color = KoonsColor.Green
            )
        }
        else -> {}
    }
}

@Composable
private fun WarningText(
    text: String,
    color: Color
) {
    Text(
        text = text,
        color = color,
        style = KoonsTypography.BodySmall,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PageButtons(
    onPrevClicked: () -> Unit,
    onNextClicked: () -> Unit,
    isFirstPage: Boolean,
    isLastPage: Boolean,
    showNext: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false,
        ) {
            when {
                isFirstPage -> {
                    Button(
                        onClick = onNextClicked,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(2f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = KoonsColor.Green, contentColor = KoonsColor.Black5),
                    ) {
                        Text(
                            text = "다음",
                            style = KoonsTypography.BodyMedium,
                            color = KoonsColor.Black5,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
                else -> {
                    Button(
                        onClick = onPrevClicked,
                        border = BorderStroke(1.dp, KoonsColor.Green),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = KoonsColor.Black5)
                    ) {
                        Text(
                            text = "이전",
                            style = KoonsTypography.BodyMedium,
                            color = KoonsColor.Green,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    if (showNext) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = onNextClicked,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(2f),
                            colors = ButtonDefaults.buttonColors(backgroundColor = KoonsColor.Green, contentColor = KoonsColor.Black5),
                        ) {
                            Text(
                                text = if (isLastPage) "가입하기" else "다음",
                                style = KoonsTypography.BodyMedium,
                                color = KoonsColor.Black5,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
