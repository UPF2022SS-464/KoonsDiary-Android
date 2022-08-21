package com.upf464.koonsdiary.presentation.ui.main.notification

sealed interface NotificationEvent {
    object OpenSettings : NotificationEvent
    data class NavigateToShareDiary(val diaryId: Int) : NotificationEvent
}
