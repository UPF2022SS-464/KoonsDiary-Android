package com.upf464.koonsdiary.presentation.ui.main.diary.detail

sealed class DetailDeletionState {
    object Closed : DetailDeletionState()
    object Deleted : DetailDeletionState()
    object Error : DetailDeletionState()
}
