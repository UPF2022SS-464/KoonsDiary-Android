package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.NotificationData

interface NotificationRemoteDataSource {

    suspend fun fetchNotificationList(): Result<List<NotificationData>>
}
