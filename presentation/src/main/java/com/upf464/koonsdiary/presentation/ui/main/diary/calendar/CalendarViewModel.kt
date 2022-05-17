package com.upf464.koonsdiary.presentation.ui.main.diary.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.usecase.calendar.FetchCalendarFlowUseCase
import com.upf464.koonsdiary.domain.usecase.diary.FetchDiaryPreviewUseCase
import com.upf464.koonsdiary.presentation.model.diary.calendar.PreviewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class CalendarViewModel @Inject constructor(
    private val fetchCalendarFlowUseCase: FetchCalendarFlowUseCase,
    private val fetchDiaryPreviewUseCase: FetchDiaryPreviewUseCase,
) : ViewModel() {

    private val _calendarStateFlow: MutableStateFlow<CalendarState>
    val calendarStateFlow: StateFlow<CalendarState>

    private val _selectDayFlow = MutableStateFlow<Int?>(null)
    val selectDayFlow = _selectDayFlow.asStateFlow()

    private val _previewStateFlow = MutableStateFlow<PreviewState>(PreviewState.None)
    val previewStateFlow = _previewStateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<CalendarEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val today = LocalDate.now()
        _calendarStateFlow = MutableStateFlow(
            CalendarState.Loading(year = today.year, month = today.monthValue)
        )
        calendarStateFlow = _calendarStateFlow.asStateFlow()
        fetchMonthlyCalendar(today.year, today.monthValue)
    }

    fun setMonth(year: Int, month: Int) {
        _selectDayFlow.value = null
        _calendarStateFlow.value = CalendarState.Loading(year = year, month = month)
        _previewStateFlow.value = PreviewState.None
        fetchMonthlyCalendar(year, month)
    }

    private fun fetchMonthlyCalendar(year: Int, month: Int) {
        viewModelScope.launch {
            fetchCalendarFlowUseCase(FetchCalendarFlowUseCase.Request(year, month))
                .collect { result ->
                    result.onSuccess { response ->
                        _calendarStateFlow.value = CalendarState.Success(
                            year = year,
                            month = month,
                            sentimentList = response.sentimentList
                        )
                    }.onFailure { error ->
                        _calendarStateFlow.value = CalendarState.UnknownError(year, month, error.message)
                    }
                }
        }
    }

    fun setPreviewDay(day: Int) {
        val date = LocalDate.of(
            _calendarStateFlow.value.year,
            _calendarStateFlow.value.month,
            day
        )
        _selectDayFlow.value = day
        _previewStateFlow.value = PreviewState.Loading(date)
        viewModelScope.launch {
            fetchDiaryPreviewUseCase(FetchDiaryPreviewUseCase.Request(date))
                .onSuccess { response ->
                    val preview = response.preview
                    _previewStateFlow.value = PreviewState.Success(
                        PreviewModel(
                            diaryId = preview.id,
                            date = preview.date,
                            content = preview.content,
                            imagePath = preview.imagePath
                        )
                    )
                }.onFailure { error ->
                    when (error) {
                        DiaryError.NoPreview -> _previewStateFlow.value = PreviewState.NoPreview(date)
                        else -> _previewStateFlow.value = PreviewState.UnknownError(error.message)
                    }
                }
        }
    }

    fun detailDiary() {
        val previewState = previewStateFlow.value
        if (previewState is PreviewState.Success) {
            _eventFlow.tryEmit(CalendarEvent.NavigateToDetail(previewState.model.diaryId))
        }
        _eventFlow.tryEmit(CalendarEvent.NavigateToDetail(2))
    }

    fun newDiary() {
        val date = LocalDate.of(
            _calendarStateFlow.value.year,
            _calendarStateFlow.value.month,
            _selectDayFlow.value ?: return
        )
        _eventFlow.tryEmit(CalendarEvent.NavigateToNewDiary(date))
    }
}
