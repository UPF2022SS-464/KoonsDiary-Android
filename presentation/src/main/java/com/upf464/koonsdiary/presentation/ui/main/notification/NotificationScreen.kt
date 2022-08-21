package com.upf464.koonsdiary.presentation.ui.main.notification

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upf464.koonsdiary.domain.model.Comment
import com.upf464.koonsdiary.domain.model.Notification
import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.model.Reaction
import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.settings.SettingsActivity
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

@Composable
internal fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
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
        notificationState = NotificationState(
            isLoading = false,
            listOf(
                Notification.GroupInvite(group = ShareGroup(name = "테스트임")),
                Notification.GroupInvite(group = ShareGroup(name = "테스트임"), isAccepted = true),
                Notification.GroupInvite(group = ShareGroup(name = "테스트임"), isAccepted = false),
                Notification.CottonReaction(answer = QuestionAnswer(), reaction = Reaction(name = "좋아요")),
                Notification.DiaryComment(diary = ShareDiary(group = ShareGroup(name = "테스트임"), content = "테스트인데", user = User(nickname = "안녕")),
                    comment = Comment(content = "하이요")),
                Notification.NewDiary(diary = ShareDiary(group = ShareGroup(name = "테스트임"), content = "테스트인데", user = User(nickname = "안녕"))),
            ),
        ),
        onAcceptGroupInvite = {},
        onRejectGroupInvite = {},
        onClickNotificationAt = {},
    )
}

@Composable
private fun NotificationScreen(
    onSettingsClicked: () -> Unit,
    notificationState: NotificationState,
    onAcceptGroupInvite: (Int) -> Unit,
    onRejectGroupInvite: (Int) -> Unit,
    onClickNotificationAt: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                onSettingsClicked = onSettingsClicked,
            )
        }
    ) {
        LazyColumn {
            items(notificationState.notificationList.size) { i ->
                when (val item = notificationState.notificationList[i]) {
                    is Notification.CottonReaction -> NotificationItem(
                        message = "${item.answer.createdDate.format(dateFormatter)}에 씻어보낸 솜사탕이 ${item.reaction.name}(을)를 받았습니다.",
                    )
                    is Notification.GroupInvite -> {
                        when (item.isAccepted) {
                            true -> NotificationItem(
                                message = "${item.group.name} 그룹에 초대되었습니다.",
                                contentPreview = "초대를 수락하셨습니다.",
                                backgroundColor = KoonsColor.Green,
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_invite),
                                        contentDescription = null,
                                        tint = KoonsColor.Green
                                    )
                                },
                                onClick = {
                                    onClickNotificationAt(i)
                                }
                            )
                            false -> NotificationItem(
                                message = "${item.group.name} 그룹에 초대되었습니다.",
                                contentPreview = "초대를 거절하셨습니다.",
                                backgroundColor = KoonsColor.Red,
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_invite),
                                        contentDescription = null,
                                        tint = KoonsColor.Red
                                    )
                                },
                                onClick = {
                                    onClickNotificationAt(i)
                                }
                            )
                            null -> NotificationItem(
                                message = "[${item.group.name}]에 초대되었습니다.",
                                onConfirm = { onAcceptGroupInvite(item.group.id) },
                                onCancel = { onRejectGroupInvite(item.group.id) },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_invite),
                                        contentDescription = null,
                                        tint = KoonsColor.Green
                                    )
                                },
                                onClick = {
                                    onClickNotificationAt(i)
                                }
                            )
                        }
                    }
                    is Notification.DiaryComment -> NotificationItem(
                        message = "[${item.diary.group.name}]에서\n${item.comment.user.nickname}님이 새 댓글을 남겼습니다.",
                        contentPreview = item.comment.content,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_heart),
                                contentDescription = null,
                                tint = KoonsColor.Red,
                            )
                        },
                        onClick = {
                            onClickNotificationAt(i)
                        }
                    )
                    is Notification.NewDiary -> NotificationItem(
                        message = "[${item.diary.group.name}]에서\n${item.diary.user.nickname}님이 새 일기를 남겼습니다.",
                        contentPreview = item.diary.content,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_invite),
                                contentDescription = null,
                                tint = KoonsColor.Green
                            )
                        },
                        onClick = {
                            onClickNotificationAt(i)
                        }
                    )
                }
            }
        }
    }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NotificationItem(
    message: String,
    contentPreview: String? = null,
    icon: @Composable (() -> Unit)? = null,
    backgroundColor: Color? = null,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .then(
                if (backgroundColor != null) Modifier
                    .padding(vertical = 4.dp)
                    .background(backgroundColor.copy(alpha = 0.2f))
                    .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                    .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp)
                else Modifier
                    .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            )
    ) {
        icon?.invoke()
        Column(
            modifier = Modifier
                .weight(1f)
                .then(if (icon != null) Modifier.padding(start = 12.dp) else Modifier),
        ) {
            Text(
                text = message,
                style = KoonsTypography.H8,
                color = KoonsColor.Black100
            )
            contentPreview?.let {
                Text(
                    text = contentPreview,
                    style = KoonsTypography.H8,
                    color = backgroundColor ?: KoonsColor.Black40,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false,
        ) {
            onConfirm?.let {
                IconButton(
                    onClick = it,
                    modifier = Modifier.padding(horizontal = 4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = KoonsColor.Green
                    )
                }
            }
            onCancel?.let {
                IconButton(
                    onClick = it,
                    modifier = Modifier.padding(horizontal = 4.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = null,
                        tint = KoonsColor.Red
                    )
                }
            }
        }
    }
}
