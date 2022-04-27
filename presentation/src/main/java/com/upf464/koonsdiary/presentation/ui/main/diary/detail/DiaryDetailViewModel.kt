package com.upf464.koonsdiary.presentation.ui.main.diary.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.usecase.diary.DeleteDiaryUseCase
import com.upf464.koonsdiary.domain.usecase.diary.FetchDiaryUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.mapper.toDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DiaryDetailViewModel @Inject constructor(
    private val fetchDiaryUseCase: FetchDiaryUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val diaryId = savedStateHandle.get<Int>(Constants.PARAM_DIARY_ID) ?: -1

    private val _diaryStateFlow = MutableStateFlow<DiaryDetailState>(DiaryDetailState.Loading)
    val diaryStateFlow = _diaryStateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<DiaryDetailEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchDiary(diaryId)
    }

    private fun fetchDiary(diaryId: Int) {
        viewModelScope.launch {
            fetchDiaryUseCase(FetchDiaryUseCase.Request(diaryId))
                .onSuccess { response ->
                    _diaryStateFlow.value = DiaryDetailState.Success(response.diary.toDetailModel())
                }.onFailure { error ->
                    _diaryStateFlow.value = DiaryDetailState.UnknownError(error.message)
                }
        }
    }

    fun delete() {
        viewModelScope.launch {
            deleteDiaryUseCase(DeleteDiaryUseCase.Request(diaryId))
                .onSuccess {
                    _eventFlow.tryEmit(DiaryDetailEvent.Deleted)
                }.onFailure {
                    _eventFlow.tryEmit(DiaryDetailEvent.DeletionError)
                }
        }
    }

    fun edit() {
        _eventFlow.tryEmit(DiaryDetailEvent.Edit)
    }
}
