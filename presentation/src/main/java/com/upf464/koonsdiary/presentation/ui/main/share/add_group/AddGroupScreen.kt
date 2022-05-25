package com.upf464.koonsdiary.presentation.ui.main.share.add_group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun AddGroupScreen(
    viewModel: AddGroupViewModel = hiltViewModel()
) {
    AddGroupScreen(
        onSave = { viewModel.save() }
    )
}

@Composable
private fun AddGroupScreen(
    onSave: () -> Unit = { }
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AddGroupAppBar(onSave = onSave)
    }
}

@Composable
private fun AddGroupAppBar(
    onSave: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "새 공유 일기장",
                style = KoonsTypography.H3,
                color = KoonsColor.Black100
            )
        },
        actions = {
            IconButton(onClick = onSave) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = KoonsColor.Green
                )
            }
        }
    )
}

