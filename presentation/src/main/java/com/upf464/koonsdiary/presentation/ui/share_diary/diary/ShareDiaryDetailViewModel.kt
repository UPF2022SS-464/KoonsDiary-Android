package com.upf464.koonsdiary.presentation.ui.share_diary.diary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.usecase.share.DeleteShareDiaryUseCase
import com.upf464.koonsdiary.domain.usecase.share.FetchCommentListUseCase
import com.upf464.koonsdiary.domain.usecase.share.FetchShareDiaryUseCase
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
internal class ShareDiaryDetailViewModel @Inject constructor(
    val dateTimeUtil: DateTimeUtil,
    private val fetchShareDiaryUseCase: FetchShareDiaryUseCase,
    private val fetchCommentListUseCase: FetchCommentListUseCase,
    savedStateHandle: SavedStateHandle,
    private val deleteShareDiaryUseCase: DeleteShareDiaryUseCase
) : ViewModel() {

    private val diaryId = savedStateHandle.get<String>(Constants.PARAM_DIARY_ID)?.toIntOrNull() ?: 0
    private var groupId = 0

    private val _diaryStateFlow = MutableStateFlow<ShareDiaryState>(ShareDiaryState.Loading)
    val diaryStateFlow = _diaryStateFlow.asStateFlow()

    private val _commentStateFlow = MutableStateFlow<ShareDiaryCommentState>(ShareDiaryCommentState.Loading)
    val commentStateFlow = _commentStateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ShareDiaryEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            fetchShareDiaryUseCase(FetchShareDiaryUseCase.Request(diaryId = diaryId))
                .onSuccess { response ->
                    _diaryStateFlow.value = ShareDiaryState.Success(response.diary)
                    groupId = response.diary.group.id
                }
                .onFailure {
                    // TODO("오류 처리")
                }
        }

        viewModelScope.launch {
            fetchCommentListUseCase(FetchCommentListUseCase.Request(diaryId = diaryId))
                .onSuccess { response ->
                    _commentStateFlow.value = ShareDiaryCommentState.Success(response.commentList)
                }
                .onFailure {
                    // TODO("오류 처리")
                }
        }
    }

    fun edit() {
        _eventFlow.tryEmit(
            ShareDiaryEvent.NavigateToEditor(
                groupId = groupId,
                diaryId = diaryId
            )
        )
    }

    fun delete() {
        viewModelScope.launch {
            deleteShareDiaryUseCase(DeleteShareDiaryUseCase.Request(diaryId))
                .onSuccess {
                    _eventFlow.tryEmit(ShareDiaryEvent.DiaryDeleted)
                }
                .onFailure {
                    // TODO("오류 처리")
                }
        }
    }
}
