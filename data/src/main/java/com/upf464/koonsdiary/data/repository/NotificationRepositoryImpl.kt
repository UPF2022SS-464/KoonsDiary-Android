package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.NotificationRemoteDataSource
import com.upf464.koonsdiary.domain.model.Notification
import com.upf464.koonsdiary.domain.repository.NotificationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val remote: NotificationRemoteDataSource
) : NotificationRepository {

    override suspend fun fetchNotificationList(): Result<List<Notification>> {
        return remote.fetchNotificationList().map { notificationList ->
            notificationList.map { it.toDomain() }
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }
}
