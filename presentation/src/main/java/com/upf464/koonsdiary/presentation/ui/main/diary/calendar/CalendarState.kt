package com.upf464.koonsdiary.presentation.ui.main.diary.calendar

import com.upf464.koonsdiary.presentation.model.diary.calendar.DateModel

sealed class CalendarState {

    abstract val year: Int
    abstract val month: Int

    data class Loading(
        override val year: Int,
        override val month: Int
    ) : CalendarState()

    data class Success(
        override val year: Int,
        override val month: Int,
        val modelList: List<DateModel>
    ) : CalendarState()

    data class UnknownError(
        override val year: Int,
        override val month: Int,
        val message: String? = null
    ) : CalendarState()
}
