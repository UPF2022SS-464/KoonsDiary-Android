package com.upf464.koonsdiary.presentation.ui.main.share.group

sealed class ShareGroupEvent {

    data class NavigateToDiary(val diaryId: Int) : ShareGroupEvent()
    data class NavigateToSettings(val groupId: Int) : ShareGroupEvent()
    data class NavigateToNewDiary(val groupId: Int) : ShareGroupEvent()
}
