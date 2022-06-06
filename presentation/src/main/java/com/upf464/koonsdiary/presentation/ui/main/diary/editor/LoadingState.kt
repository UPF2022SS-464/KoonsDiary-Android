package com.upf464.koonsdiary.presentation.ui.main.diary.editor

sealed class LoadingState {
    object Loading : LoadingState()
    object Closed : LoadingState()
    object Error : LoadingState()
}
