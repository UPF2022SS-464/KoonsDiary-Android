package com.upf464.koonsdiary.presentation.ui.main.diary.detail

sealed class DiaryDetailEvent {

    object Deleted : DiaryDetailEvent()

    object DeletionError : DiaryDetailEvent()

    object Edit : DiaryDetailEvent()
}
