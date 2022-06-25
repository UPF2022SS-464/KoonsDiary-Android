package com.upf464.koonsdiary.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun ChangeContentDialog(
    title: String,
    subTitle: String,
    hint: String,
    content: String,
    onContentChanged: (String) -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(KoonsColor.Black5)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onCancel) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = KoonsColor.Red
                    )
                }
                IconButton(onClick = onConfirm) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = KoonsColor.Green
                    )
                }
            }

            Text(
                text = title,
                style = KoonsTypography.H4,
                color = KoonsColor.Black100
            )
            Text(
                text = subTitle,
                style = KoonsTypography.H8,
                color = KoonsColor.Black60,
                modifier = Modifier.padding(top = 4.dp)
            )

            BasicTextField(
                value = content,
                onValueChange = onContentChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textStyle = KoonsTypography.BodySmall.copy(color = KoonsColor.Black100),
                decorationBox = { innerTextField ->
                    innerTextField()
                    if (content.isEmpty()) {
                        Text(
                            text = hint,
                            style = KoonsTypography.BodySmall,
                            color = KoonsColor.Black60,
                        )
                    }
                }
            )
        }
    }
}
