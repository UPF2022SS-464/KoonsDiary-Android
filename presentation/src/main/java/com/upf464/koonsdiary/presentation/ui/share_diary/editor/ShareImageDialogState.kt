package com.upf464.koonsdiary.presentation.ui.share_diary.editor

sealed class ShareImageDialogState {

    object Closed : ShareImageDialogState()

    data class Opened(val index: Int) : ShareImageDialogState()
}
