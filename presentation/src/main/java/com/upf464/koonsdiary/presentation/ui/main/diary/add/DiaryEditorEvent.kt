package com.upf464.koonsdiary.presentation.ui.main.diary.add

sealed class DiaryEditorEvent {

    data class Success(val diaryId: Int) : DiaryEditorEvent()
}
