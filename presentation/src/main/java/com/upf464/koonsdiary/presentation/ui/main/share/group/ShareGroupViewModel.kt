package com.upf464.koonsdiary.presentation.ui.main.share.group

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.usecase.share.FetchGroupUseCase
import com.upf464.koonsdiary.domain.usecase.share.FetchShareDiaryListUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.common.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareGroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val dateTimeUtil: DateTimeUtil,
    private val fetchGroupUseCase: FetchGroupUseCase,
    private val fetchShareDiaryListUseCase: FetchShareDiaryListUseCase
) : ViewModel() {

    private val groupId = savedStateHandle.get<Int>(Constants.PARAM_GROUP_ID) ?: 0

    private val _eventFlow = MutableSharedFlow<ShareGroupEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    private val _groupStateFlow = MutableStateFlow<ShareGroupState>(ShareGroupState.Loading)
    val groupStateFlow = _groupStateFlow.asStateFlow()

    private val _diaryListStateFlow = MutableStateFlow<ShareDiaryListState>(ShareDiaryListState.Loading)
    val diaryListStateFlow = _diaryListStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            fetchGroupUseCase(FetchGroupUseCase.Request(groupId))
                .onSuccess { response ->
                    _groupStateFlow.value = ShareGroupState.Success(response.group)
                }.onFailure {
                    // TODO("오류 처리")
                }
        }

        viewModelScope.launch {
            fetchShareDiaryListUseCase(FetchShareDiaryListUseCase.Request(groupId))
                .onSuccess { response ->
                    _diaryListStateFlow.value = ShareDiaryListState.Success(response.diaryList)
                }.onFailure {
                    // TODO("오류 처리")
                }
        }
    }

    fun navigateToDiary(diary: ShareDiary) {
        _eventFlow.tryEmit(ShareGroupEvent.NavigateToDiary(diary.id))
    }

    fun navigateToGroupSettings() {
        _eventFlow.tryEmit(ShareGroupEvent.NavigateToSettings(groupId))
    }
}
