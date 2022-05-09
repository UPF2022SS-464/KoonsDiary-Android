package com.upf464.koonsdiary.presentation.ui.main.diary.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.model.diary.calendar.PreviewModel
import com.upf464.koonsdiary.presentation.ui.main.diary.DiaryNavigation
import java.time.LocalDate

@Composable
internal fun CalendarScreen(
    navController: NavController,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val calendarState by viewModel.calendarStateFlow.collectAsState()
    val previewState by viewModel.previewStateFlow.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is CalendarEvent.NavigateToDetail -> {
                    navController.navigate(DiaryNavigation.DETAIL.route + "/${event.diaryId}")
                }
                else -> {
                    /* TODO("일기 작성 화면 실행") */
                }
            }
        }
    }

    CalendarScreen(
        calendarState = calendarState,
        previewState = previewState,
        { year, month -> viewModel.setMonth(year, month) },
        { day -> viewModel.setPreviewDay(day) },
        { viewModel.detailDiary() },
        { viewModel.newDiary() }
    )
}

@Composable
internal fun CalendarScreen(
    calendarState: CalendarState,
    previewState: PreviewState,
    onMonthChanged: (Int, Int) -> Unit,
    onDateClicked: (Int) -> Unit,
    onPreviewClicked: () -> Unit,
    onNewDiaryClicked: () -> Unit
) {
    Column {
        Text(text = "캘린더입니다.")
        
        when (calendarState) {
            is CalendarState.Loading -> { }
            is CalendarState.Success -> { }
            is CalendarState.UnknownError -> {
                ErrorDialog()
                return
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        when (previewState) {
            is PreviewState.Loading -> PreviewLoading(date = previewState.date)
            is PreviewState.NoPreview -> {
                Button(onClick = { onNewDiaryClicked() }) {
                    Text("일기 작성")
                }
            }
            PreviewState.None -> {}
            is PreviewState.Success -> PreviewItem(previewState.model)
            is PreviewState.UnknownError -> {
                ErrorDialog()
                return
            }
        }
    }
}

@Composable
fun ErrorDialog() {
    AlertDialog(
        onDismissRequest = { },
        buttons = { },
        title = { Text(text = "오류 발생") },
        text = { Text(text = "오류가 발생했습니다") },
    )
}

@Composable
fun PreviewItem(
    model: PreviewModel
) {
    Box(
        modifier = Modifier.padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(20.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.month_day, model.date.monthValue, model.date.dayOfMonth),
                    modifier = Modifier.padding(20.dp, 4.dp)
                )
                AsyncImage(
                    model = model.imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Divider(
                color = Color.DarkGray,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .width(1.dp)
            )
            Text(
                text = model.content,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                modifier = Modifier.padding(end = 20.dp, top = 10.dp, bottom = 10.dp)
            )
        }
    }
}

@Composable
fun PreviewLoading(
    date: LocalDate
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.LightGray)
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.month_day, date.monthValue, date.dayOfMonth),
            modifier = Modifier.padding(start = 20.dp, top = 4.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun Preview() {
//    PreviewLoading(year = 2022, month = 4, day = 26)
    PreviewItem(
        PreviewModel(
            1,
            LocalDate.of(2022, 4, 26),
            "정말 긴 문장인데 이게 다 나오는지 확인하기 위함임 이걸 복붙해야겠지 정말 긴 문장인데 이게 다 나오는지 확인하기 위함임 이걸 복붙해야겠지 정말 긴 문장인데 이게 다 나오는지 확인하기 위함임 이걸 복붙해야겠지 정말 긴 문장인데 이게 다 나오는지 확인하기 위함임 이걸 복붙해야겠지 정말 긴 문장인데 이게 다 나오는지 확인하기 위함임 이걸 복붙해야겠지 ",
            "https://news.samsungdisplay.com/wp-content/uploads/2018/08/8.jpg"
        )
    )
}
