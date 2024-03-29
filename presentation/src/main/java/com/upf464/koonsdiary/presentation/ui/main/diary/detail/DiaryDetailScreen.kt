package com.upf464.koonsdiary.presentation.ui.main.diary.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryDetailModel
import com.upf464.koonsdiary.presentation.ui.main.diary.DiaryNavigation

@Composable
internal fun DiaryDetailScreen(
    viewModel: DiaryDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val diaryState by viewModel.diaryStateFlow.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                DiaryDetailEvent.Edit -> {
                    navController.navigate(DiaryNavigation.EDITOR.route + "/${viewModel.diaryId}")
                }
            }
        }
    }

    when (viewModel.deletionStateFlow.collectAsState().value) {
        DetailDeletionState.Deleted -> {
            DeletedDialog(
                onConfirm = { navController.popBackStack() }
            )
        }
        DetailDeletionState.Error -> {
            DeletedDialog(
                isError = true,
                onConfirm = { navController.popBackStack() }
            )
        }
        DetailDeletionState.Closed -> {}
    }

    DiaryDetailScreen(
        diaryState = diaryState,
        onDelete = { viewModel.delete() },
        onEdit = { viewModel.edit() }
    )
}

@Composable
private fun DeletedDialog(
    onConfirm: () -> Unit,
    isError: Boolean = false
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isError) {
                Text(text = "오류가 발생했습니다.")
            } else {
                Text(text = "삭제되었습니다.")
            }
            TextButton(
                onClick = onConfirm,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("확인")
            }
        }
    }
}

@Composable
private fun DiaryDetailScreen(
    diaryState: DiaryDetailState,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        DetailTopBar(
            onDelete = onDelete,
            onEdit = onEdit
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            when (diaryState) {
                DiaryDetailState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(20.dp)
                    )
                }
                is DiaryDetailState.Success -> {
                    DetailSuccessBody(model = diaryState.model)
                }
                is DiaryDetailState.UnknownError -> {
                    Text(text = diaryState.message ?: "empty error")
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

@Composable
private fun ColumnScope.DetailSuccessBody(model: DiaryDetailModel) {
    Text(
        text = stringResource(
            id = R.string.year_month_day,
            model.date.year,
            model.date.monthValue,
            model.date.dayOfMonth
        ),
        modifier = Modifier.padding(top = 16.dp)
    )

    if (model.imageList.isNotEmpty()) {
        DetailImagePager(imageList = model.imageList)
    }

    Text(
        text = model.content,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )

    Spacer(modifier = Modifier.weight(1f))

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.diary_detail_sentiment))
        }
    }
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
