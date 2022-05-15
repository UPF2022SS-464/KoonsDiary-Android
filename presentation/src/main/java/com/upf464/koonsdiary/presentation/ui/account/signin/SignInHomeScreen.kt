package com.upf464.koonsdiary.presentation.ui.account.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.account.SignInNavigation
import com.upf464.koonsdiary.presentation.ui.account.signup.SignUpType
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

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
                    navController.navigate(SignInNavigation.SIGN_IN_EMAIL.route)
                SignInHomeEvent.NavigateToEmailSignUp ->
                    navController.navigate(SignInNavigation.SIGN_UP.route + "/${SignUpType.EMAIL.name}")
                SignInHomeEvent.Success ->
                    Toast.makeText(context, "로그인 성공", Toast.LENGTH_LONG).show()
                SignInHomeEvent.NavigateToKakaoSignUp ->
                    navController.navigate(SignInNavigation.SIGN_UP.route + "/${SignUpType.KAKAO.name}")
                SignInHomeEvent.UnknownError ->
                    Toast.makeText(context, "에러 발생", Toast.LENGTH_LONG).show()
            }
        }
    }

    SignInHomeScreen(
        onEmailClicked = { viewModel.signInWithEmail() },
        onKakaoClicked = { viewModel.signInWithKakao() },
        onSignUpClicked = { viewModel.signUpWithEmail() }
    )
}

@Composable
private fun SignInHomeScreen(
    onEmailClicked: () -> Unit = {},
    onKakaoClicked: () -> Unit = {},
    onSignUpClicked: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(2f))

        Text(
            text = buildAnnotatedString {
                append("당신의 ")
                withStyle(SpanStyle(color = KoonsColor.Red)) {
                    append("나쁜 감정")
                }
                append("은 ")
                withStyle(SpanStyle(color = KoonsColor.Green)) {
                    append("쿤다")
                }
                append("가")
            },
            color = KoonsColor.Black100,
            style = KoonsTypography.H3
        )
        Text(
            text = "처리해 드릴게요!",
            style = KoonsTypography.H3,
            color = KoonsColor.Black100,
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_koon),
            contentDescription = null,
            tint = KoonsColor.Green,
            modifier = Modifier
                .size(300.dp)
                .padding(32.dp)
        )
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = KoonsColor.Green)) {
                    append("쿤다")
                }
                append("와 함께 일기를 써볼까요?")
            },
            color = KoonsColor.Black100,
            style = KoonsTypography.H4
        )

        Spacer(modifier = Modifier.weight(2f))

        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(KoonsColor.Green)
                .clickable(onClick = onEmailClicked)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_account),
                tint = KoonsColor.Black5,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 16.dp)
                    .size(36.dp)
            )
            Text(
                text = "로그인",
                color = KoonsColor.Black5,
                style = KoonsTypography.BodyMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Divider(
                color = KoonsColor.Black60,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 32.dp, end = 16.dp)
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
                    .padding(start = 16.dp, end = 32.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
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

        Spacer(modifier = Modifier.weight(2f))

        Text(
            text = buildAnnotatedString {
                append("아직 ")
                withStyle(style = SpanStyle(color = KoonsColor.Green)) {
                    append("쿤다 ")
                }
                append("회원이 아니신가요?")
            },
            modifier = Modifier
                .padding(bottom = 48.dp)
                .clickable(onClick = onSignUpClicked),
            style = KoonsTypography.BodyRegular,
            color = KoonsColor.Black100
        )
    }
}
