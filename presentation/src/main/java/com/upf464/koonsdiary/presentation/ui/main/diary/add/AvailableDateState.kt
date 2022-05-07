package com.upf464.koonsdiary.presentation.ui.main.diary.add

sealed class AvailableDateState {

    data class Success(
        val year: Int,
        val month: Int,
        val dateList: List<Boolean>
    ) : AvailableDateState()

    data class Loading(
        val year: Int,
        val month: Int
    ) : AvailableDateState()

    object Closed : AvailableDateState()
}
