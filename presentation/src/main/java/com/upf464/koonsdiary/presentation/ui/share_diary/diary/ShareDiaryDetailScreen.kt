package com.upf464.koonsdiary.presentation.ui.share_diary.diary

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.share_diary.ShareDiaryNavigation
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography
import java.time.LocalDateTime

@Composable
internal fun ShareDiaryDetailScreen(
    viewModel: ShareDiaryDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                ShareDiaryEvent.DiaryDeleted ->
                    (context as Activity).finish()
                is ShareDiaryEvent.NavigateToEditor ->
                    navController.navigate(ShareDiaryNavigation.EDITOR.route + "/${event.groupId}/${event.diaryId}")
            }
        }
    }
    
    ShareDiaryDetailScreen(
        diaryState = viewModel.diaryStateFlow.collectAsState().value,
        commentState = viewModel.commentStateFlow.collectAsState().value,
        onDelete = { viewModel.delete() },
        onEdit = { viewModel.edit() },
        formatDateTime = { viewModel.dateTimeUtil.formatDateTimeToBefore(it) }
    )
}

@Composable
private fun ShareDiaryDetailScreen(
    diaryState: ShareDiaryState = ShareDiaryState.Loading,
    commentState: ShareDiaryCommentState = ShareDiaryCommentState.Loading,
    onDelete: () -> Unit = { },
    onEdit: () -> Unit = { },
    formatDateTime: (LocalDateTime) -> String = { "" }
) {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        DetailTopBar(
            onDelete = onDelete,
            onEdit = onEdit
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            item {
                when (diaryState) {
                    ShareDiaryState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(20.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    is ShareDiaryState.Success -> {
                        val diary = diaryState.diary

                        if (diary.imageList.isNotEmpty()) {
                            DetailImagePager(imageList = diary.imageList)
                        }

                        Text(
                            text = diary.content,
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        )

                        Text(
                            text = stringResource(id = R.string.shareDiary_of, diary.user.nickname),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }

            when (commentState) {
                ShareDiaryCommentState.Loading -> {

                }
                is ShareDiaryCommentState.Success -> {
                    items(commentState.commentList) { comment ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
                            AsyncImage(
                                model = comment.user.image.path,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                            )
                            Column {
                                Row {
                                    Text(
                                        text = comment.user.nickname,
                                        style = KoonsTypography.BodyMoreSmall,
                                        color = KoonsColor.Black100
                                    )
                                    Text(
                                        text = formatDateTime(comment.createdDate),
                                        style = KoonsTypography.BodyMoreSmall,
                                        color = KoonsColor.Black60
                                    )
                                }
                                Text(
                                    text = comment.content,
                                    style = KoonsTypography.BodyMoreSmall,
                                    color = KoonsColor.Black100
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailTopBar(
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    TopAppBar(
        title = { },
        actions = {
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun DetailImagePager(imageList: List<DiaryImage>) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        HorizontalPager(count = imageList.size) { index ->
            val image = imageList[index]
            Column {
                AsyncImage(
                    model = image.imagePath,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = image.comment,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}
