package com.upf464.koonsdiary.presentation.ui.share_diary.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.usecase.share.AddShareDiaryUseCase
import com.upf464.koonsdiary.domain.usecase.share.FetchShareDiaryUseCase
import com.upf464.koonsdiary.domain.usecase.share.UpdateShareDiaryUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.mapper.toDomain
import com.upf464.koonsdiary.presentation.mapper.toEditorModel
import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ShareEditorViewModel @Inject constructor(
    private val addDiaryUseCase: AddShareDiaryUseCase,
    private val updateDiaryUseCase: UpdateShareDiaryUseCase,
    private val fetchDiaryUseCase: FetchShareDiaryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val diaryId = savedStateHandle.get<String>(Constants.PARAM_DIARY_ID)?.toIntOrNull()
    private val groupId = savedStateHandle.get<String>(Constants.PARAM_GROUP_ID)?.toIntOrNull() ?: 0

    private val _imageListFlow = MutableStateFlow<List<DiaryImageModel>>(emptyList())
    val imageListFlow = _imageListFlow.asStateFlow()

    val contentFlow = MutableStateFlow("")

    val showAddImageFlow = imageListFlow.map { it.size < MAX_IMAGE_LIST_SIZE }
    private val _imageDialogStateFlow = MutableStateFlow<ShareImageDialogState>(ShareImageDialogState.Closed)
    val imageDialogStateFlow = _imageDialogStateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ShareEditorEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    private val _loadingStateFlow = MutableStateFlow(
        if (diaryId != null) ShareLoadingState.Loading
        else ShareLoadingState.Closed
    )
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    init {
        diaryId?.let { diaryId ->
            viewModelScope.launch {
                fetchDiaryUseCase(FetchShareDiaryUseCase.Request(diaryId))
                    .onSuccess { response ->
                        val diary = response.diary
                        _imageListFlow.value = diary.imageList.map { it.toEditorModel() }
                        contentFlow.value = diary.content
                        _loadingStateFlow.value = ShareLoadingState.Closed
                    }
                    .onFailure {
                        _loadingStateFlow.value = ShareLoadingState.Error
                    }
            }
        }
    }

    fun addImage(imagePath: String) {
        _imageListFlow.value = _imageListFlow.value + DiaryImageModel(imagePath)
    }

    fun openImageDialog(index: Int) {
        _imageDialogStateFlow.value = ShareImageDialogState.Opened(index)
    }

    fun closeImageDialog() {
        _imageDialogStateFlow.value = ShareImageDialogState.Closed
    }

    fun deleteImage(index: Int) {
        val imageList = imageListFlow.value
        _imageListFlow.value = imageList.subList(0, index) + imageList.subList(index + 1, imageList.size)
        _imageDialogStateFlow.value = ShareImageDialogState.Closed
    }

    fun save() {
        viewModelScope.launch {
            if (diaryId == null) {
                addDiaryUseCase(
                    AddShareDiaryUseCase.Request(
                        groupId = groupId,
                        content = contentFlow.value,
                        imageList = imageListFlow.value.map { it.toDomain() }
                    )
                ).onSuccess { response ->
                    _eventFlow.tryEmit(ShareEditorEvent.AddSuccess(response.diaryId))
                }.onFailure {
                    // TODO("오류 처리")
                }
            } else {
                updateDiaryUseCase(
                    UpdateShareDiaryUseCase.Request(
                        diaryId = diaryId,
                        content = contentFlow.value,
                        imageList = imageListFlow.value.map { it.toDomain() }
                    )
                ).onSuccess { response ->
                    _eventFlow.tryEmit(ShareEditorEvent.EditSuccess(response.diaryId))
                }.onFailure {
                    // TODO("오류 처리")
                }
            }
        }
    }

    companion object {
        private const val MAX_IMAGE_LIST_SIZE = 3
    }
}
