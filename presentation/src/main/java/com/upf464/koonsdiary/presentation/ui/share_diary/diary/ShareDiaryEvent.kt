package com.upf464.koonsdiary.presentation.ui.share_diary.diary

sealed class ShareDiaryEvent {

    data class NavigateToEditor(val diaryId: Int) : ShareDiaryEvent()
    object DiaryDeleted : ShareDiaryEvent()
}
