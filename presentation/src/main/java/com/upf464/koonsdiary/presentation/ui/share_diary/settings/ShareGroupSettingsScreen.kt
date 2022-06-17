package com.upf464.koonsdiary.presentation.ui.share_diary.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun ShareGroupSettingsScreen(
    viewModel: ShareGroupSettingsViewModel = hiltViewModel(),
    navController: NavController
) {

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.setImage(uri.toString())
        }
    }

    ShareGroupSettingsScreen(
        onBackPressed = { navController.popBackStack() },
        onDeleteGroupClicked = { viewModel.deleteGroup() },
        groupState = viewModel.groupStateFlow.collectAsState().value,
        onGroupNameClicked = { viewModel.openGroupNameDialog() },
        groupNameState = viewModel.groupNameStateFlow.collectAsState().value,
        groupName = viewModel.groupNameFlow.collectAsState().value,
        onGroupNameChanged = { viewModel.groupNameFlow.value = it },
        onSaveGroupName = { viewModel.saveGroupName() },
        onCloseGroupName = { viewModel.closeGroupNameDialog() },
        onImageClicked = { galleryLauncher.launch("image/*") },
        onImageReset = { viewModel.setImage(null) },
        onNicknameClicked = { },
        nicknameState = viewModel.nicknameStateFlow.collectAsState().value,
        nickname = "",
        onNicknameChanged = { },
        onSaveNickname = { },
        onCloseNickname = { },
        onKickClicked = { },
        keyword = "",
        onKeywordChanged = { },
        onInviteUser = { },
        onDeleteButtonClicked = { },
        onInviteButtonClicked = { },
    )
}

@Composable
private fun ShareGroupSettingsScreen(
    onBackPressed: () -> Unit,
    onDeleteGroupClicked: () -> Unit,
    groupState: ShareGroupState = ShareGroupState.Loading,
    onGroupNameClicked: () -> Unit,
    groupNameState: ShareGroupDialogState,
    groupName: String,
    onGroupNameChanged: (String) -> Unit,
    onSaveGroupName: () -> Unit,
    onCloseGroupName: () -> Unit,
    onImageClicked: () -> Unit,
    onImageReset: () -> Unit,
    onNicknameClicked: () -> Unit,
    nicknameState: ShareGroupDialogState,
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    onSaveNickname: () -> Unit,
    onCloseNickname: () -> Unit,
    onKickClicked: (User) -> Unit,
    keyword: String,
    onKeywordChanged: (String) -> Unit,
    onInviteUser: (User) -> Unit,
    onDeleteButtonClicked: (Int) -> Unit,
    onInviteButtonClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            SettingsTopBar(
                onDeleteGroupClicked = onDeleteGroupClicked,
                onBackPressed = onBackPressed
            )
        }
    ) {
        when (groupNameState) {
            ShareGroupDialogState.Closed -> {}
            ShareGroupDialogState.Loading -> LoadingDialog()
            ShareGroupDialogState.Opened -> ChangeContentDialog(
                title = "닉네임을 변경하시겠습니까?",
                subTitle = "닉네임은 해당 공유 일기장에서만 적용됩니다.",
                content = groupName,
                onContentChanged = onGroupNameChanged,
                onClose = onCloseGroupName,
                onSave = onSaveGroupName
            )
        }

        when (groupState) {
            is ShareGroupState.Success -> {
                GroupContent(
                    group = groupState.group,
                    onImageClicked = onImageClicked,
                    onResetClicked = onImageReset,
                    onGroupNameClicked = onGroupNameClicked
                )
            }
        }
    }
}

@Composable
private fun SettingsTopBar(
    onDeleteGroupClicked: () -> Unit,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "공유일기장 설정",
                style = KoonsTypography.H5,
                color = KoonsColor.Black100
            )
        },
        actions = {
            IconButton(onClick = onDeleteGroupClicked) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = KoonsColor.Red
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

@Composable
private fun GroupContent(
    group: ShareGroup,
    onImageClicked: () -> Unit,
    onResetClicked: () -> Unit,
    onGroupNameClicked: () -> Unit
) {
    Card(
        backgroundColor = KoonsColor.Black5,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, KoonsColor.Black100),
        modifier = Modifier
            .padding(start = 48.dp, end = 48.dp, top = 72.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.height(IntrinsicSize.Min)) {
            Column(
                modifier = Modifier.padding(top = 48.dp, bottom = 36.dp, start = 32.dp, end = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (group.imagePath == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(KoonsColor.Black40)
                            .clickable(onClick = onImageClicked)
                    )
                } else {
                    Box {
                        AsyncImage(
                            model = group.imagePath,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable(onClick = onImageClicked)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_circle_cross),
                            contentDescription = null,
                            tint = KoonsColor.Red,
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(CircleShape)
                                .clickable(onClick = onResetClicked)
                                .align(Alignment.TopStart)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 48.dp, top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = group.name,
                        style = KoonsTypography.H7,
                        color = KoonsColor.Black100,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(KoonsColor.Black10)
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                    IconButton(onClick = onGroupNameClicked) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = KoonsColor.Black100
                        )
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(6f))
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(12.dp)
                        .background(KoonsColor.Green)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ChangeContentDialog(
    title: String,
    subTitle: String,
    content: String,
    onContentChanged: (String) -> Unit,
    onClose: () -> Unit,
    onSave: () -> Unit
) {
    Dialog(onDismissRequest = onClose) {
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
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = KoonsColor.Red
                    )
                }
                IconButton(onClick = onSave) {
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
                            text = "닉네임을 입력해주세요",
                            style = KoonsTypography.BodySmall,
                            color = KoonsColor.Black60,
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun LoadingDialog() {
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator(color = KoonsColor.Green)
    }
}
