package com.upf464.koonsdiary.presentation.ui.share_diary.settings

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.model.share.add_group.SearchUserResultModel
import com.upf464.koonsdiary.presentation.ui.components.ShareUserListRow
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun ShareGroupSettingsScreen(
    viewModel: ShareGroupSettingsViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.setImage(uri.toString())
        }
    }

    ShareGroupSettingsScreen(
        onBackPressed = { (context as Activity).finish() },
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
        me = viewModel.meFlow.collectAsState().value,
        onNicknameClicked = { viewModel.openNicknameDialog() },
        nicknameState = viewModel.nicknameStateFlow.collectAsState().value,
        nickname = viewModel.nicknameFlow.collectAsState().value,
        onNicknameChanged = { viewModel.nicknameFlow.value = it },
        onSaveNickname = { viewModel.saveNickname() },
        onCloseNickname = { viewModel.closeNicknameDialog() },
        onKickClicked = { viewModel.kickUser(it) },
        keyword = viewModel.keywordFlow.collectAsState().value,
        onKeywordChanged = { viewModel.keywordFlow.value = it },
        isWaitingResult = viewModel.searchWaitingFlow.collectAsState().value,
        searchResult = viewModel.searchResultFlow.collectAsState().value,
        onInviteUser = { viewModel.addInviteUser(it) },
        inviteUserList = viewModel.inviteUserListFlow.collectAsState().value,
        onDeleteButtonClicked = {  },
        onInviteButtonClicked = { viewModel.inviteUserList() },
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
    me: User,
    onNicknameClicked: () -> Unit,
    nicknameState: ShareGroupDialogState,
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    onSaveNickname: () -> Unit,
    onCloseNickname: () -> Unit,
    onKickClicked: (Int) -> Unit,
    keyword: String,
    onKeywordChanged: (String) -> Unit,
    isWaitingResult: Boolean,
    searchResult: SearchUserResultModel,
    onInviteUser: (User) -> Unit,
    inviteUserList: List<User>,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (groupNameState) {
                ShareGroupDialogState.Closed -> {}
                ShareGroupDialogState.Loading -> LoadingDialog()
                ShareGroupDialogState.Opened -> ChangeContentDialog(
                    title = "공유일기장 제목을 변경하시겠습니까?",
                    subTitle = "변경한 내용으로 친구들에게도 보여집니다.",
                    content = groupName,
                    onContentChanged = onGroupNameChanged,
                    onClose = onCloseGroupName,
                    onSave = onSaveGroupName
                )
            }

            when (nicknameState) {
                ShareGroupDialogState.Closed -> {}
                ShareGroupDialogState.Loading -> LoadingDialog()
                ShareGroupDialogState.Opened -> ChangeContentDialog(
                    title = "닉네임을 변경하시겠습니까?",
                    subTitle = "닉네임은 해당 공유 일기장에서만 적용됩니다.",
                    content = nickname,
                    onContentChanged = onNicknameChanged,
                    onClose = onCloseNickname,
                    onSave = onSaveNickname
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

                    Text(
                        text = "개인설정",
                        style = KoonsTypography.BodySmall,
                        color = KoonsColor.Black60,
                        modifier = Modifier.padding(top = 48.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 16.dp, end = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_account),
                            contentDescription = null,
                            tint = KoonsColor.Green
                        )

                        Text(
                            text = "닉네임 : ${me.nickname}",
                            style = KoonsTypography.BodyMedium,
                            color = KoonsColor.Black100,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f)
                        )

                        TextButton(onClick = onNicknameClicked) {
                            Text(
                                text = "수정하기",
                                style = KoonsTypography.BodyMedium,
                                color = KoonsColor.Green
                            )
                        }
                    }
                    Divider(color = KoonsColor.Black20)

                    Text(
                        text = "함께하는 친구들 설정",
                        style = KoonsTypography.BodySmall,
                        color = KoonsColor.Black60,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    Text(
                        text = "일기장에 함께하는 친구들",
                        style = KoonsTypography.BodyMedium,
                        color = KoonsColor.Black100,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    ShareUserListRow(
                        userList = groupState.group.userList,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        onDeleteClicked = onKickClicked
                    )

                    Text(
                        text = "일기장에 초대할 친구들 (초대 보내기)",
                        style = KoonsTypography.BodyMedium,
                        color = KoonsColor.Black100,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    ShareUserListRow(
                        userList = inviteUserList,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    )

                    BasicTextField(
                        value = keyword,
                        onValueChange = onKeywordChanged,
                        textStyle = KoonsTypography.BodyMedium.copy(color = KoonsColor.Black100),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Column {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box {
                                        if (keyword.isEmpty()) {
                                            Text(
                                                text = "초대할 아이디를 입력해주세요.",
                                                style = KoonsTypography.BodyMedium,
                                                color = KoonsColor.Black40
                                            )
                                        }
                                        innerTextField()
                                    }
                                    if (isWaitingResult) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(
                                                with(LocalDensity.current) {
                                                    KoonsTypography.BodyMedium.fontSize.toDp()
                                                }
                                            ),
                                            strokeWidth = 2.dp
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    Divider(color = KoonsColor.Black100, modifier = Modifier.padding(top = 4.dp))

                    if (searchResult.userList.isEmpty() && searchResult.keyword.isNotEmpty()) {
                        Text(
                            text = "검색 결과가 없습니다.",
                            style = KoonsTypography.BodyMedium,
                            color = KoonsColor.Black60,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        searchResult.userList.forEach { user ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = user.image.path,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        append(user.username.substringBefore(searchResult.keyword))
                                        withStyle(SpanStyle(color = KoonsColor.Black100)) {
                                            append(searchResult.keyword)
                                        }
                                        append(user.username.substringAfter(searchResult.keyword))
                                    },
                                    color = KoonsColor.Black60,
                                    style = KoonsTypography.BodyMedium,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "초대하기",
                                    style = KoonsTypography.BodyMedium,
                                    color = KoonsColor.Black100,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(KoonsColor.Black40)
                                        .clickable { onInviteUser(user) }
                                        .padding(vertical = 2.dp, horizontal = 16.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "일기장에 초대한 친구들 (초대 수락 대기중)",
                        style = KoonsTypography.BodyMedium,
                        color = KoonsColor.Black100,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    ShareUserListRow(
                        // TODO: 필터 추가
                        userList = groupState.group.userList,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    )

                    Text(
                        text = "일기장에서 탈퇴한 친구들",
                        style = KoonsTypography.BodyMedium,
                        color = KoonsColor.Black100,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    ShareUserListRow(
                        // TODO: 필터 추가
                        userList = groupState.group.userList,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        onDeleteClicked = { user ->
                            onDeleteButtonClicked(user)
                        }
                    )
                    
                    Button(
                        onClick = onInviteButtonClicked,
                        colors = ButtonDefaults.buttonColors(backgroundColor = KoonsColor.Green),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 48.dp)
                    ) {
                        Text(
                            text = "설정완료",
                            style = KoonsTypography.BodyMedium,
                            color = KoonsColor.Black5,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
                ShareGroupState.Loading -> LoadingDialog()
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
