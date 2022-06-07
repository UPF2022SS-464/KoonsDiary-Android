package com.upf464.koonsdiary.presentation.ui.share_diary.editor

sealed class ShareLoadingState {
    object Loading : ShareLoadingState()
    object Closed : ShareLoadingState()
    object Error : ShareLoadingState()
}
