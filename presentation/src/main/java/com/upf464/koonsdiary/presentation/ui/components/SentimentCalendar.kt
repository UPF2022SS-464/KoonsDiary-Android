package com.upf464.koonsdiary.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun SentimentCalendar(
    firstWeekDay: Int,
    monthLength: Int,
    sentimentList: List<Sentiment?>,
    paddingHorizontal: Dp = 32.dp,
    showHeader: Boolean = true,
    headerStyle: TextStyle = KoonsTypography.H7,
    content: @Composable ColumnScope.(day: Int, weekDay: Int, sentiment: Sentiment?) -> Unit = { _, _, _ -> }
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = paddingHorizontal)
    ) {
        if (showHeader) {
            CalendarWeekHeader(headerStyle)
        }

        ((1 - firstWeekDay)..monthLength step 7).forEach { weekStart ->
            CalendarWeek(
                startDay = maxOf(1, weekStart),
                sentimentList = sentimentList.subList(maxOf(0, weekStart - 1), minOf(weekStart + 6, monthLength)),
                emptyStart = -minOf(weekStart - 1, 0),
                emptyEnd = maxOf(weekStart + 6 - monthLength, 0),
                content = content
            )
        }
    }
}

@Composable
private fun CalendarWeekHeader(
    style: TextStyle
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        val weekDayArray = stringArrayResource(id = R.array.WeekDay)
        weekDayArray.forEachIndexed { i, weekDay ->
            Text(
                text = weekDay,
                style = style,
                color = when (i) {
                    0 -> KoonsColor.Red
                    6 -> KoonsColor.Green
                    else -> KoonsColor.Black100
                },
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CalendarWeek(
    startDay: Int,
    sentimentList: List<Sentiment?>,
    emptyStart: Int,
    emptyEnd: Int,
    content: @Composable ColumnScope.(day: Int, weekDay: Int, sentiment: Sentiment?) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        (0 until 7).forEach { weekDay ->
            if (weekDay < emptyStart || 6 - weekDay < emptyEnd) {
                Spacer(modifier = Modifier.weight(1f))
            } else {
                CalendarItem(
                    day = startDay + weekDay - emptyStart,
                    sentiment = sentimentList[weekDay - emptyStart],
                    weekDay = weekDay,
                    content = content
                )
            }
        }
    }
}

@Composable
private fun RowScope.CalendarItem(
    day: Int,
    sentiment: Sentiment?,
    weekDay: Int,
    content: @Composable ColumnScope.(day: Int, weekDay: Int, sentiment: Sentiment?) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f)
    ) {
        content(day, weekDay, sentiment)
    }
}
