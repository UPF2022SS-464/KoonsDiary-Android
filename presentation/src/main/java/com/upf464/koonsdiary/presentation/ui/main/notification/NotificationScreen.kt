package com.upf464.koonsdiary.presentation.ui.main.notification

import android.content.Intent
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upf464.koonsdiary.presentation.ui.settings.SettingsActivity
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                NotificationEvent.OpenSettings -> {
                    context.startActivity(Intent(context, SettingsActivity::class.java))
                }
            }
        }
    }

    NotificationScreen(
        onSettingsClicked = { viewModel.openSettings() },
    )
}

@Composable
private fun NotificationScreen(
    onSettingsClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                onSettingsClicked = onSettingsClicked,
            )
        }
    ) { }
}

@Composable
private fun AppBar(
    onSettingsClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "알림",
                style = KoonsTypography.H3,
                color = KoonsColor.Black100
            )
        },
        actions = {
            IconButton(
                onClick = onSettingsClicked
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = KoonsColor.Green
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}
