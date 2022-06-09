package com.upf464.koonsdiary.presentation.ui.share_diary.settings

sealed class ShareGroupDialogState {
    object Closed : ShareGroupDialogState()
    object Opened : ShareGroupDialogState()
    object Loading : ShareGroupDialogState()
}
