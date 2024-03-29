package com.upf464.koonsdiary.presentation.ui.main.share.add_group

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.model.share.add_group.SearchUserResultModel
import com.upf464.koonsdiary.presentation.ui.components.ShareUserListRow
import com.upf464.koonsdiary.presentation.ui.main.share.ShareNavigation
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun AddGroupScreen(
    navController: NavController,
    viewModel: AddGroupViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AddGroupEvent.NavigateToGroup -> {
                    navController.popBackStack()
                    navController.navigate(ShareNavigation.GROUP_DETAIL.route + "/${event.groupId}")
                }
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.setGroupImage(uri.toString())
        }
    }

    AddGroupScreen(
        onSave = { viewModel.save() },
        imagePath = viewModel.model.imagePathFlow.collectAsState().value,
        groupName = viewModel.model.nameFlow.collectAsState().value,
        onGroupNameChange = { viewModel.model.nameFlow.value = it },
        onImageClicked = { galleryLauncher.launch("image/*") },
        onImageResetClicked = { viewModel.resetGroupImage() },
        inviteUserList = viewModel.model.inviteUserListUser.collectAsState().value,
        onInviteDeleteClicked = { viewModel.deleteInviteUserOn(it) },
        keyword = viewModel.keywordFlow.collectAsState().value,
        onKeywordChanged = { viewModel.keywordFlow.value = it },
        searchResult = viewModel.searchResultFlow.collectAsState().value,
        onInviteClicked = { viewModel.addInviteUser(it) },
        isWaitingResult = viewModel.waitingResultFlow.collectAsState().value,
        onBackPressed = { (context as? Activity)?.finish() }
    )
}

@Composable
private fun AddGroupScreen(
    onSave: () -> Unit = { },
    imagePath: String? = null,
    groupName: String = "",
    onGroupNameChange: (String) -> Unit = { },
    onImageClicked: () -> Unit = { },
    onImageResetClicked: () -> Unit = { },
    inviteUserList: List<User> = listOf(),
    onInviteDeleteClicked: (Int) -> Unit = { },
    keyword: String = "",
    onKeywordChanged: (String) -> Unit = { },
    searchResult: SearchUserResultModel = SearchUserResultModel("", listOf()),
    onInviteClicked: (User) -> Unit = { },
    isWaitingResult: Boolean = false,
    onBackPressed: () -> Unit = { }
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AddGroupAppBar(
            onSave = onSave,
            onBackPressed = onBackPressed
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            GroupInformation(
                imagePath = imagePath,
                groupName = groupName,
                onGroupNameChange = onGroupNameChange,
                onImageClicked = onImageClicked,
                onResetClicked = onImageResetClicked
            )

            // TODO: 같은 사람 중복 추가 안되도록 수정
            ShareUserListRow(
                userList = inviteUserList,
                onDeleteClicked = onInviteDeleteClicked,
                contentPadding = PaddingValues(top = 48.dp, start = 24.dp, end = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            )

            InviteUserSearch(
                keyword = keyword,
                onKeywordChanged = onKeywordChanged,
                searchResult = searchResult,
                onInviteClicked = onInviteClicked,
                isWaitingResult = isWaitingResult
            )
        }
    }
}

@Composable
private fun AddGroupAppBar(
    onSave: () -> Unit,
    onBackPressed: () -> Unit
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
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier.padding(top = 32.dp),
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun GroupInformation(
    imagePath: String?,
    groupName: String,
    onGroupNameChange: (String) -> Unit,
    onImageClicked: () -> Unit,
    onResetClicked: () -> Unit
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
                modifier = Modifier.padding(vertical = 48.dp, horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imagePath == null) {
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
                            model = imagePath,
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

                BasicTextField(
                    value = groupName,
                    onValueChange = onGroupNameChange,
                    textStyle = KoonsTypography.H7.copy(textAlign = TextAlign.Center),
                    decorationBox = { innerTextField ->
                        if (groupName.isEmpty()) {
                            Text(
                                text = "공유 일기장 이름",
                                color = KoonsColor.Black40,
                                style = KoonsTypography.H7,
                                textAlign = TextAlign.Center
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
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
@OptIn(ExperimentalMaterialApi::class)
private fun InviteUserSearch(
    keyword: String,
    onKeywordChanged: (String) -> Unit,
    searchResult: SearchUserResultModel,
    onInviteClicked: (User) -> Unit,
    isWaitingResult: Boolean
) {
    Text(
        text = "초대할 친구",
        style = KoonsTypography.H8,
        color = KoonsColor.Black100,
        modifier = Modifier.padding(horizontal = 32.dp)
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
        modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 12.dp)
    )
    Divider(color = KoonsColor.Black100, modifier = Modifier.padding(top = 4.dp))

    if (searchResult.userList.isEmpty() && searchResult.keyword.isNotEmpty()) {
        Text(
            text = "검색 결과가 없습니다.",
            style = KoonsTypography.BodyMedium,
            color = KoonsColor.Black60,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            textAlign = TextAlign.Center
        )
    } else {
        searchResult.userList.forEach { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 12.dp),
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
                        .clickable { onInviteClicked(user) }
                        .padding(vertical = 2.dp, horizontal = 16.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(32.dp))
}
