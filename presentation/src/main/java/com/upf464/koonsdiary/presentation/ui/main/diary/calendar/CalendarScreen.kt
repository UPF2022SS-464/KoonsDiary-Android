package com.upf464.koonsdiary.presentation.ui.main.diary.calendar

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.components.SentimentCalendar
import com.upf464.koonsdiary.presentation.ui.main.diary.DiaryNavigation
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography
import com.upf464.koonsdiary.presentation.ui.theme.colorOf
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
                is CalendarEvent.NavigateToDetail ->
                    navController.navigate(DiaryNavigation.DETAIL.route + "/${event.diaryId}")
                else ->
                    navController.navigate(DiaryNavigation.EDITOR.route)
            }
        }
    }

    CalendarScreen(
        calendarState = calendarState,
        previewState = previewState,
        today = viewModel.today,
        selectedDay = viewModel.selectDayFlow.collectAsState().value,
        onMonthChanged = { year, month -> viewModel.setMonth(year, month) },
        onDateClicked = { day -> viewModel.setPreviewDay(day) },
        onPreviewClicked = {
            when (previewState) {
                is PreviewState.NoPreview -> viewModel.newDiary()
                is PreviewState.Success -> viewModel.detailDiary()
                else -> {}
            }
        },
    )
}

@Composable
private fun CalendarScreen(
    calendarState: CalendarState,
    previewState: PreviewState,
    today: LocalDate,
    selectedDay: Int?,
    onMonthChanged: (Int, Int) -> Unit,
    onDateClicked: (Int) -> Unit,
    onPreviewClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalendarHeader(
            year = calendarState.year,
            month = calendarState.month,
            onMonthChanged = onMonthChanged
        )

        when (calendarState) {
            is CalendarState.Loading -> {}
            is CalendarState.Success -> {
                SentimentCalendar(
                    firstWeekDay = calendarState.startWeekDay,
                    monthLength = calendarState.lastDay,
                    sentimentList = calendarState.sentimentList
                ) { day, weekDay, sentiment ->
                    val isToday = calendarState.year == today.year && calendarState.month == today.monthValue && day == today.dayOfMonth
                    val isSelected = selectedDay == day

                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 4.dp)
                            .clip(CircleShape)
                            .size(28.dp)
                            .then(
                                when {
                                    isSelected -> Modifier.background(KoonsColor.Green)
                                    isToday -> Modifier.background(KoonsColor.Black10)
                                    else -> Modifier
                                }
                            )
                            .clickable {
                                onDateClicked(day)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$day",
                            style = KoonsTypography.BodyRegular,
                            color = when {
                                isSelected -> KoonsColor.Black5
                                weekDay == 0 -> KoonsColor.Red
                                weekDay == 6 -> KoonsColor.Green
                                else -> KoonsColor.Black100
                            }
                        )
                    }

                    if (sentiment != null) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_koon),
                            contentDescription = null,
                            tint = colorOf(sentiment),
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            is CalendarState.UnknownError -> {
                ErrorDialog()
                return
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CalendarPreview(
            state = previewState,
            onPreviewClicked = onPreviewClicked
        )
    }
}

@Composable
private fun CalendarHeader(
    year: Int,
    month: Int,
    onMonthChanged: (Int, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if (month == 1) onMonthChanged(year - 1, 12)
                else onMonthChanged(year, month - 1)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
            )
        }
        Text(
            text = stringResource(id = R.string.year_month, year, month),
            style = KoonsTypography.H4,
            color = KoonsColor.Black100
        )
        IconButton(
            onClick = {
                if (month == 12) onMonthChanged(year + 1, 1)
                else onMonthChanged(year, month + 1)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ErrorDialog() {
    AlertDialog(
        onDismissRequest = { },
        buttons = { },
        title = { Text(text = "오류 발생") },
        text = { Text(text = "오류가 발생했습니다") },
    )
}

@Composable
private fun CalendarPreview(
    state: PreviewState,
    onPreviewClicked: () -> Unit
) {
    val (month, day) = when (state) {
        is PreviewState.Loading -> state.date.monthValue to state.date.dayOfMonth
        is PreviewState.NoPreview -> state.date.monthValue to state.date.dayOfMonth
        is PreviewState.Success -> state.model.date.monthValue to state.model.date.dayOfMonth
        else -> return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.month_day, month, day),
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(KoonsColor.Black5)
                .padding(vertical = 4.dp, horizontal = 16.dp),
            style = KoonsTypography.BodySmall,
            color = KoonsColor.Black100
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp))
                .background(KoonsColor.Black5)
                .clickable(onClick = onPreviewClicked)
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (state) {
                is PreviewState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = KoonsColor.Green
                    )
                }
                is PreviewState.NoPreview -> {
                    Text(
                        text = "일기가 없어요! 일기를 써 볼까요?",
                        style = KoonsTypography.BodySmall,
                        color = KoonsColor.Black100,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_new_diary),
                        tint = KoonsColor.Green,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                is PreviewState.Success -> {
                    AsyncImage(
                        model = state.model.imagePath,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .aspectRatio(1f)
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp)
                            .width(1.dp),
                        color = KoonsColor.Black100
                    )
                    val lineHeight = KoonsTypography.BodySmall.fontSize
                    Text(
                        text = state.model.content,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = KoonsTypography.BodySmall,
                        color = KoonsColor.Black100,
                        modifier = Modifier
                            .heightIn(min = with(LocalDensity.current) { (lineHeight * 3).toDp() })
                            .fillMaxWidth()
                    )
                }
                else -> return
            }
        }
    }
}
