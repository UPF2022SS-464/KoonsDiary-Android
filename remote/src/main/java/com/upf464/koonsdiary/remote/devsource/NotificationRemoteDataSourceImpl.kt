package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.NotificationData
import com.upf464.koonsdiary.data.source.NotificationRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRemoteDataSourceImpl @Inject constructor() : NotificationRemoteDataSource {

    override suspend fun fetchNotificationList(): Result<List<NotificationData>> {
        return Result.success(listOf())
    }
}
