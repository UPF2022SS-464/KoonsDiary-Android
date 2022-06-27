package com.upf464.koonsdiary.presentation.ui.settings.profile

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.components.UserImageSelector
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun ProfileScreen(
    state: ProfileState,
    onBackPressed: () -> Unit,
    onImageClicked: (Int) -> Unit,
    onConfirm: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                onBackPressed = onBackPressed,
                onConfirm = onConfirm,
                showConfirm = state.selectedIndex != -1,
            )
        }
    ) {
        UserImageSelector(
            imageList = state.imageList,
            onImageClicked = onImageClicked,
            selectedIndex = state.selectedIndex
        )
    }
}

@Composable
private fun AppBar(
    onBackPressed: () -> Unit,
    onConfirm: () -> Unit,
    showConfirm: Boolean
) {
    TopAppBar(
        title = {
            Text(
                text = "프로필 변경",
                style = KoonsTypography.H3,
                color = KoonsColor.Black100
            )
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = KoonsColor.Black60
                )
            }
        },
        actions = {
            if (showConfirm) {
                IconButton(onClick = onConfirm) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = KoonsColor.Green
                    )
                }
            }
        }
    )
}
