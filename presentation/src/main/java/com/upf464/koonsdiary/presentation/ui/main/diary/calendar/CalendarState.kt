package com.upf464.koonsdiary.presentation.ui.main.diary.calendar

import com.upf464.koonsdiary.domain.model.Sentiment
import java.time.DayOfWeek
import java.time.LocalDate

sealed class CalendarState(
    val year: Int,
    val month: Int
) {

    val startWeekDay: Int
    val lastDay: Int

    init {
        val startDay = LocalDate.of(year, month, 1)
        startWeekDay = (startDay.dayOfWeek.ordinal - DayOfWeek.MONDAY.ordinal + 1) % 7
        lastDay = startDay.lengthOfMonth()
    }

    class Loading(
        year: Int,
        month: Int
    ) : CalendarState(year, month)

    class Success(
        year: Int,
        month: Int,
        val sentimentList: List<Sentiment?>
    ) : CalendarState(year, month)

    class UnknownError(
        year: Int,
        month: Int,
        val message: String? = null
    ) : CalendarState(year, month)
}
