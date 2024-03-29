package com.upf464.koonsdiary.presentation.ui.main.share.group

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.ui.components.ShareUserListRow
import com.upf464.koonsdiary.presentation.ui.share_diary.ShareDiaryActivity
import com.upf464.koonsdiary.presentation.ui.share_diary.ShareDiaryNavigation
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography
import java.time.LocalDateTime

@Composable
internal fun ShareGroupScreen(
    viewModel: ShareGroupViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ShareGroupEvent.NavigateToDiary -> {
                    context.startActivity(
                        Intent(context, ShareDiaryActivity::class.java).apply {
                            putExtra(Constants.EXTRA_SHARE_DIARY_ROUTE, ShareDiaryNavigation.DIARY_DETAIL.route)
                            putExtra(Constants.EXTRA_SHARE_DIARY_ID, event.diaryId)
                        }
                    )
                }
                is ShareGroupEvent.NavigateToSettings -> {
                    context.startActivity(
                        Intent(context, ShareDiaryActivity::class.java).apply {
                            putExtra(Constants.EXTRA_SHARE_DIARY_ROUTE, ShareDiaryNavigation.SETTINGS.route)
                            putExtra(Constants.PARAM_GROUP_ID, event.groupId)
                        }
                    )
                }
                is ShareGroupEvent.NavigateToNewDiary -> {
                    context.startActivity(
                        Intent(context, ShareDiaryActivity::class.java).apply {
                            putExtra(Constants.EXTRA_SHARE_DIARY_ROUTE, ShareDiaryNavigation.EDITOR.route)
                            putExtra(Constants.PARAM_GROUP_ID, event.groupId)
                        }
                    )
                }
            }
        }
    }

    ShareGroupScreen(
        groupState = viewModel.groupStateFlow.collectAsState().value,
        diaryListState = viewModel.diaryListStateFlow.collectAsState().value,
        onBackPressed = { navController.popBackStack() },
        onSettingsPressed = { viewModel.navigateToGroupSettings() },
        onDiaryClicked = { viewModel.navigateToDiary(it) },
        formatDateTime = { viewModel.dateTimeUtil.formatDateTimeToBefore(it) },
        onNewDiaryClicked = { viewModel.navigateToAddDiary() }
    )
}

@Composable
private fun ShareGroupScreen(
    groupState: ShareGroupState = ShareGroupState.Loading,
    diaryListState: ShareDiaryListState = ShareDiaryListState.Loading,
    onBackPressed: () -> Unit = { },
    onSettingsPressed: () -> Unit = { },
    formatDateTime: (LocalDateTime) -> String = { "" },
    onDiaryClicked: (ShareDiary) -> Unit = { },
    onNewDiaryClicked: () -> Unit = { }
) {
    if (groupState is ShareGroupState.Success) {
        val group = groupState.group

        Scaffold(
            topBar = {
                ShareGroupAppBar(
                    groupName = group.name,
                    onBackPressed = onBackPressed,
                    onSettingsPressed = onSettingsPressed
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNewDiaryClicked,
                    backgroundColor = KoonsColor.Black5
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_new_diary),
                        contentDescription = null,
                        tint = KoonsColor.Green
                    )
                }
            }
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

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

                    Text(
                        text = "함께하는 친구들",
                        style = KoonsTypography.BodyMedium,
                        color = KoonsColor.Black100,
                        modifier = Modifier.padding(start = 24.dp)
                    )

                    ShareUserListRow(userList = group.userList)
                }

                when (diaryListState) {
                    ShareDiaryListState.Loading -> {
                        item {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(16.dp)
                                        .size(32.dp)
                                )
                            }
                        }
                    }
                    // TODO: UI 변경 반영
                    is ShareDiaryListState.Success -> {
                        items(diaryListState.diaryList) { diary ->
                            DiaryCard(
                                diary = diary,
                                formatDateTime = formatDateTime,
                                onDiaryClicked = onDiaryClicked
                            )
                        }
                    }
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

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun DiaryCard(
    diary: ShareDiary,
    formatDateTime: (LocalDateTime) -> String,
    onDiaryClicked: (ShareDiary) -> Unit
) {
    Card(
        backgroundColor = KoonsColor.Black5,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
        onClick = { onDiaryClicked(diary) }
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)) {
            Row {
                Text(
                    text = "${diary.user.nickname}(${diary.user.username})",
                    style = KoonsTypography.BodyMoreSmall,
                    color = KoonsColor.Black100
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = formatDateTime(diary.createdDate),
                    style = KoonsTypography.BodyMoreSmall,
                    color = KoonsColor.Black60
                )
            }

            val image = diary.imageList.firstOrNull()
            if (image != null) {
                AsyncImage(
                    model = image.imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }

            Text(
                text = diary.content,
                style = KoonsTypography.BodySmall,
                color = KoonsColor.Black100,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_comment),
                    contentDescription = null,
                    tint = KoonsColor.Red,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${diary.commentCount}",
                    style = KoonsTypography.BodyMoreSmall,
                    color = KoonsColor.Black100,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
