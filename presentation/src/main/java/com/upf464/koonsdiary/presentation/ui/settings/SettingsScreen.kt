package com.upf464.koonsdiary.presentation.ui.settings

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.components.ChangeContentDialog
import com.upf464.koonsdiary.presentation.ui.settings.password.PasswordScreen
import com.upf464.koonsdiary.presentation.ui.settings.profile.ProfileScreen
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography
import com.upf464.koonsdiary.presentation.ui.theme.koonsSwitchColor

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    BackHandler(onBack = viewModel::back)

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is SettingsEvent.Finish -> {
                    (context as Activity).finish()
                }
                SettingsEvent.ShowBiometricFailedError -> {
                    Toast.makeText(context, R.string.biometric_error_failed, Toast.LENGTH_SHORT).show()
                }
                SettingsEvent.ShowBiometricTimeoutError -> {
                    Toast.makeText(context, R.string.biometric_error_timeout, Toast.LENGTH_SHORT).show()
                }
                SettingsEvent.ShowBiometricUnavailableError -> {
                    Toast.makeText(context, R.string.biometric_error_unavailable, Toast.LENGTH_SHORT).show()
                }
                SettingsEvent.ShowLockBiometricError -> {
                    Toast.makeText(context, R.string.biometric_error_lockout, Toast.LENGTH_SHORT).show()
                }
                SettingsEvent.ShowNoBiometricError -> {
                    Toast.makeText(context, R.string.biometric_error_no_credential, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val state = viewModel.settingsStateFlow.collectAsState().value
    val passwordState = viewModel.passwordStateFlow.collectAsState().value
    val profileState = viewModel.profileStateFlow.collectAsState().value

    when {
        passwordState.isShowing -> {
            PasswordScreen(
                state = passwordState,
                length = viewModel.passwordLengthFlow.collectAsState().value,
                maxLength = SettingsViewModel.PASSWORD_MAX_LENGTH,
                onNumberClicked = viewModel::clickPasswordNumber,
                onBackspaceClicked = viewModel::clearOneNumber,
                onExitClicked = viewModel::closePasswordDialog,
                onPasswordSuccessConfirmed = viewModel::closePasswordDialog
            )
        }
        profileState.isShowing -> {
            ProfileScreen(
                state = profileState,
                onBackPressed = viewModel::back,
                onImageClicked = viewModel::selectImageAt,
                onConfirm = viewModel::confirmImage
            )
        }
        else -> {
            SettingsScreen(
                state = state,
                onBackPressed = viewModel::back,
                onImageClicked = viewModel::openProfileScreen,
                onNicknameClicked = viewModel::openNicknameDialog,
                onUsePasswordChanged = viewModel::changeUsePassword,
                onChangePasswordClicked = viewModel::openPasswordDialog,
                onUseBiometricChanged = viewModel::changeUseBiometric,
            )
        }
    }

    if (state.isEditingNickname) {
        ChangeContentDialog(
            title = "닉네임을 변경하시겠습니까?",
            subTitle = "",
            hint = "닉네임을 입력해주세요",
            content = viewModel.nicknameFlow.collectAsState().value,
            onContentChanged = { viewModel.nicknameFlow.value = it },
            onCancel = viewModel::closeNicknameDialog,
            onConfirm = viewModel::confirmNickname
        )
    }
}

@Composable
private fun SettingsScreen(
    state: SettingsState,
    onBackPressed: () -> Unit,
    onImageClicked: () -> Unit,
    onNicknameClicked: () -> Unit,
    onUsePasswordChanged: (Boolean) -> Unit,
    onChangePasswordClicked: () -> Unit,
    onUseBiometricChanged: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                onBackPressed = onBackPressed
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            UserImage(
                image = state.userImage,
                onImageClicked = onImageClicked
            )
            UserNickname(
                nickname = state.nickname,
                onNicknameClicked = onNicknameClicked
            )
            PersonalSettings(
                username = state.username,
                email = state.email,
            )
            CustomSettings(
                usePassword = state.usePassword,
                useBiometric = state.useBiometric,
                onUsePasswordChanged = onUsePasswordChanged,
                onUseBiometricChanged = onUseBiometricChanged,
                onChangePasswordClicked = onChangePasswordClicked
            )
        }
    }
}

@Composable
private fun AppBar(
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "설정",
                style = KoonsTypography.H3,
                color = KoonsColor.Black100
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackPressed
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = KoonsColor.Black60
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    )
}

@Composable
private fun ColumnScope.UserImage(
    image: User.Image,
    onImageClicked: () -> Unit,
) {
    AsyncImage(
        model = image.path,
        contentDescription = null,
        modifier = Modifier
            .padding(top = 48.dp)
            .size(160.dp)
            .clip(CircleShape)
            .align(Alignment.CenterHorizontally)
            .clickable(onClick = onImageClicked),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun ColumnScope.UserNickname(
    nickname: String,
    onNicknameClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        Text(
            text = "닉네임 : $nickname",
            style = KoonsTypography.H7,
            color = KoonsColor.Black100
        )
        IconButton(onClick = onNicknameClicked) {
            Icon(
                painter = painterResource(id = R.drawable.ic_new_diary),
                contentDescription = null,
                tint = KoonsColor.Green
            )
        }
    }
}

@Composable
private fun PersonalSettings(
    username: String,
    email: String,
) {
    Text(
        text = "개인 설정",
        style = KoonsTypography.BodySmall,
        color = KoonsColor.Black60,
        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
    )
    Row(
        modifier = Modifier.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_account),
            contentDescription = null,
            tint = KoonsColor.Green
        )
        Text(
            text = "아이디 : $username",
            style = KoonsTypography.H7,
            color = KoonsColor.Black100,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
    Divider(color = KoonsColor.Black20)
    Row(
        modifier = Modifier.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = null,
            tint = KoonsColor.Green
        )
        Text(
            text = "이메일 : $email",
            style = KoonsTypography.H7,
            color = KoonsColor.Black100,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
    Divider(color = KoonsColor.Black20)
}


@Composable
private fun CustomSettings(
    usePassword: Boolean,
    useBiometric: Boolean,
    onUsePasswordChanged: (Boolean) -> Unit,
    onChangePasswordClicked: () -> Unit,
    onUseBiometricChanged: (Boolean) -> Unit,
) {
    Text(
        text = "맞춤 설정",
        style = KoonsTypography.BodySmall,
        color = KoonsColor.Black60,
        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onUsePasswordChanged(!usePassword)
            }
            .padding(vertical = 12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            tint = KoonsColor.Green
        )
        Text(
            text = "비밀번호 설정",
            style = KoonsTypography.H7,
            color = KoonsColor.Black100,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
        Switch(
            checked = usePassword,
            onCheckedChange = null,
            colors = koonsSwitchColor()
        )
    }

    if (usePassword) {
        Divider(color = KoonsColor.Black20)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onChangePasswordClicked
                )
                .padding(vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = KoonsColor.Green
            )
            Text(
                text = "비밀번호 바꾸기",
                style = KoonsTypography.H7,
                color = KoonsColor.Black100,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = null,
                tint = KoonsColor.Black60
            )
        }
        Divider(color = KoonsColor.Black20)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onUseBiometricChanged(!useBiometric)
                }
                .padding(vertical = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_fingerprint),
                contentDescription = null,
                tint = KoonsColor.Green
            )
            Text(
                text = "생체정보로 잠금해제",
                style = KoonsTypography.H7,
                color = KoonsColor.Black100,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            )
            Switch(
                checked = useBiometric,
                onCheckedChange = null,
                colors = koonsSwitchColor()
            )
        }
        Divider(color = KoonsColor.Black20)
    }
}
