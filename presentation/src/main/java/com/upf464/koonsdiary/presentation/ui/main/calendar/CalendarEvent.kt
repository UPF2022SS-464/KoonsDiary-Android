package com.upf464.koonsdiary.presentation.ui.main.calendar

import java.time.LocalDate

sealed class CalendarEvent {

    data class NavigateToDetail(
        val diaryId: Int
    ) : CalendarEvent()

    data class NavigateToNewDiary(
        val date: LocalDate
    ) : CalendarEvent()
}
