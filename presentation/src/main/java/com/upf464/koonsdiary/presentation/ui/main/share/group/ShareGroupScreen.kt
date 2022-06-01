package com.upf464.koonsdiary.presentation.ui.main.share.group

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun ShareGroupScreen(
    viewModel: ShareGroupViewModel = hiltViewModel()
) {
    ShareGroupScreen(
        groupState = ShareGroupState.Success(
            ShareGroup(
                name = "그룹1",
                imagePath = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg",
                userList = listOf(
                    User(username = "Username1", nickname = "nickname1", image = User.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"))
                )
            )
        )
    )
}

@Composable
private fun ShareGroupScreen(
    groupState: ShareGroupState = ShareGroupState.Loading,
    diaryListState: ShareDiaryListState = ShareDiaryListState.Loading,
    onBackPressed: () -> Unit = { },
    onSettingsPressed: () -> Unit = { }
) {
    if (groupState is ShareGroupState.Success) {
        val group = groupState.group

        Column(modifier = Modifier.fillMaxSize()) {
            ShareGroupAppBar(
                groupName = group.name,
                onBackPressed = onBackPressed,
                onSettingsPressed = onSettingsPressed
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                if (group.imagePath != null) {
                    AsyncImage(
                        model = group.imagePath,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun ShareGroupAppBar(
    groupName: String,
    onBackPressed: () -> Unit,
    onSettingsPressed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = groupName,
                style = KoonsTypography.H5,
                color = KoonsColor.Black100
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = onSettingsPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null,
                    tint = KoonsColor.Green
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}
