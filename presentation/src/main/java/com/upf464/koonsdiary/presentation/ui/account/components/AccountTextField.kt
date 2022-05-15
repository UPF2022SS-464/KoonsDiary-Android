package com.upf464.koonsdiary.presentation.ui.account.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
fun AccountTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeHolder: String,
    paddingTop: Dp = 0.dp,
    isPassword: Boolean = false
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        textStyle = KoonsTypography.BodyRegular,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingTop),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        decorationBox = { innerTextField ->
            Column {
                Box(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeHolder,
                            color = KoonsColor.Black40,
                            style = KoonsTypography.BodyRegular
                        )
                    }
                    innerTextField()
                }
                Divider(color = if (value.isEmpty()) KoonsColor.Black40 else KoonsColor.Black100)
            }
        }
    )
}
