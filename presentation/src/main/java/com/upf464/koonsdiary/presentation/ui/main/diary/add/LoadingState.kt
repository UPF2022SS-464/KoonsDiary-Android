package com.upf464.koonsdiary.presentation.ui.main.diary.add

sealed class LoadingState {
    object Loading : LoadingState()
    object Closed : LoadingState()
    object Error : LoadingState()
}
