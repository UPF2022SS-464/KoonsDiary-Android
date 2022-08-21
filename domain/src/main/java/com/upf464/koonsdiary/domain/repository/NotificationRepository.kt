package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.Notification

interface NotificationRepository {

    suspend fun fetchNotificationList(): Result<List<Notification>>
}
