package com.upf464.koonsdiary.presentation.ui.settings.password

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun PasswordScreen(
    state: PasswordState,
    length: Int,
    maxLength: Int,
    onNumberClicked: (Int) -> Unit,
    onBackspaceClicked: () -> Unit,
    onExitClicked: () -> Unit,
    onPasswordSuccessConfirmed: () -> Unit,
) {
    if (state.isSuccess) {
        Dialog(onDismissRequest = {}) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 400.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(KoonsColor.Black5),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "비밀번호가 설정 되었습니다",
                    style = KoonsTypography.H4,
                    color = KoonsColor.Black100,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "비밀번호 분실 시 찾을 수 없으니 주의 하세요",
                    style = KoonsTypography.H8,
                    color = KoonsColor.Black60,
                )
                Text(
                    text = "확인",
                    style = KoonsTypography.H6,
                    color = KoonsColor.Black5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(KoonsColor.Green)
                        .clickable(onClick = onPasswordSuccessConfirmed)
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = if (!state.isConfirm) "새 비밀번호를 입력해 주세요" else "비밀번호를 한 번 더 입력해 주세요",
            style = KoonsTypography.H3,
            color = KoonsColor.Black100
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(top = 32.dp)
        ) {
            repeat(maxLength) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .then(
                            if (it < length) Modifier
                                .clip(CircleShape)
                                .background(color = KoonsColor.Green)
                            else Modifier.border(
                                width = 3.dp,
                                color = KoonsColor.Green,
                                shape = CircleShape
                            )
                        )
                )
            }
        }

        Row(
            modifier = Modifier.padding(top = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            NumberPad(number = 1, onNumberClicked = onNumberClicked)
            NumberPad(number = 2, onNumberClicked = onNumberClicked)
            NumberPad(number = 3, onNumberClicked = onNumberClicked)
        }
        Row(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            NumberPad(number = 4, onNumberClicked = onNumberClicked)
            NumberPad(number = 5, onNumberClicked = onNumberClicked)
            NumberPad(number = 6, onNumberClicked = onNumberClicked)
        }
        Row(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            NumberPad(number = 7, onNumberClicked = onNumberClicked)
            NumberPad(number = 8, onNumberClicked = onNumberClicked)
            NumberPad(number = 9, onNumberClicked = onNumberClicked)
        }
        Row(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ButtonPad(text = "나가기", onClick = onExitClicked)
            NumberPad(number = 0, onNumberClicked = onNumberClicked)
            ButtonPad(text = "지우기", onClick = onBackspaceClicked)
        }
    }
}

@Composable
private fun NumberPad(
    number: Int,
    onNumberClicked: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(color = KoonsColor.Green)
            .clickable {
                onNumberClicked(number)
            }
    ) {
        Text(
            text = number.toString(),
            style = KoonsTypography.H2,
            color = KoonsColor.Black5,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ButtonPad(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(color = KoonsColor.Green)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = KoonsTypography.H4,
            color = KoonsColor.Black5,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
