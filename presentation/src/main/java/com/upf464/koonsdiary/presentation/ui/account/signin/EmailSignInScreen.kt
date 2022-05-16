package com.upf464.koonsdiary.presentation.ui.account.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.account.SignInActivity
import com.upf464.koonsdiary.presentation.ui.account.SignInNavigation
import com.upf464.koonsdiary.presentation.ui.account.components.AccountTextField
import com.upf464.koonsdiary.presentation.ui.account.signup.SignUpType
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun EmailSignInScreen(
    viewModel: EmailSignInViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                EmailSignInEvent.Success ->
                    (context as? SignInActivity)?.startMain()
                EmailSignInEvent.NavigateToEmailSignUp ->
                    navController.navigate(SignInNavigation.SIGN_UP.route + "/${SignUpType.EMAIL.name}")
                EmailSignInEvent.NavigateToKakaoSignUp ->
                    navController.navigate(SignInNavigation.SIGN_UP.route + "/${SignUpType.KAKAO.name}")
            }
        }
    }

    EmailSignInScreen(
        signInState = viewModel.signInState.collectAsState().value,
        username = viewModel.usernameFlow.collectAsState().value,
        password = viewModel.passwordFlow.collectAsState().value,
        onUsernameChanged = { viewModel.usernameFlow.value = it },
        onPasswordChanged = { viewModel.passwordFlow.value = it },
        onSignInClicked = { viewModel.signIn() },
        onKakaoClicked = { viewModel.signInWithKakao() },
        onSignUpClicked = { viewModel.signUpWithEmail() }
    )
}

@Composable
private fun EmailSignInScreen(
    signInState: EmailSignInState = EmailSignInState.Closed,
    username: String = "",
    password: String = "",
    onUsernameChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onSignInClicked: () -> Unit = {},
    onKakaoClicked: () -> Unit = {},
    onSignUpClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 48.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "로그인",
            style = KoonsTypography.H3,
            color = KoonsColor.Black100
        )
        AccountTextField(
            value = username,
            onValueChanged = onUsernameChanged,
            placeHolder = "아이디 또는 이메일을 입력해주세요",
            paddingTop = 48.dp
        )
        AccountTextField(
            value = password,
            onValueChanged = onPasswordChanged,
            placeHolder = "비밀번호를 입력해주세요",
            paddingTop = 32.dp,
            isPassword = true
        )

        when (signInState) {
            EmailSignInState.Closed -> {
                Spacer(modifier = Modifier.height(84.dp))
            }
            EmailSignInState.Failed -> {
                Text(
                    text = """
                        아이디, 이메일 또는 비밀번호를 잘못 입력하셨습니다
                        입력하신 내용이 맞는지 다시 한 번 확인해 주세요
                    """.trimIndent(),
                    color = KoonsColor.Red,
                    style = KoonsTypography.BodySmall,
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 24.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(KoonsColor.Green)
                .height(52.dp)
                .clickable(onClick = onSignInClicked)
        ) {
            Text(
                text = "로그인",
                color = KoonsColor.Black5,
                style = KoonsTypography.BodyMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Divider(
                color = KoonsColor.Black60,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 16.dp)
            )
            Text(
                text = "또는",
                color = KoonsColor.Black60,
                style = KoonsTypography.BodyMedium
            )
            Divider(
                color = KoonsColor.Black60,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(KoonsColor.KakaoBackground)
                .clickable(onClick = onKakaoClicked)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_kakao),
                tint = KoonsColor.KakaoContent,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 16.dp)
                    .size(36.dp)
            )
            Text(
                text = "카카오 계정으로 시작하기",
                modifier = Modifier.align(Alignment.Center),
                color = KoonsColor.KakaoContent,
                style = KoonsTypography.BodyMedium
            )
        }

        Text(
            text = buildAnnotatedString {
                append("아직 ")
                withStyle(style = SpanStyle(color = KoonsColor.Green)) {
                    append("쿤다 ")
                }
                append("회원이 아니신가요?")
            },
            modifier = Modifier
                .clickable(onClick = onSignUpClicked)
                .align(Alignment.CenterHorizontally),
            style = KoonsTypography.BodyRegular,
            color = KoonsColor.Black100
        )
    }
}
