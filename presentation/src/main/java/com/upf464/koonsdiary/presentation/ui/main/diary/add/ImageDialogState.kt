package com.upf464.koonsdiary.presentation.ui.main.diary.add

sealed class ImageDialogState {

    object Closed : ImageDialogState()

    data class Opened(val index: Int) : ImageDialogState()
}
