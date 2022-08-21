package com.upf464.koonsdiary.presentation.ui.main.notification

import com.upf464.koonsdiary.domain.model.Notification

data class NotificationState(
    val isLoading: Boolean = true,
    val notificationList: List<Notification> = listOf(),
)
