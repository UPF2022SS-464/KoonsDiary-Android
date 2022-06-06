package com.upf464.koonsdiary.presentation.ui.main.diary.editor

sealed class DiaryLoadingState {
    object Loading : DiaryLoadingState()
    object Closed : DiaryLoadingState()
    object Error : DiaryLoadingState()
}
