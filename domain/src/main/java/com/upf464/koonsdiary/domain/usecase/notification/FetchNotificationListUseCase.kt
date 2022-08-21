package com.upf464.koonsdiary.domain.usecase.notification

import com.upf464.koonsdiary.domain.model.Notification
import com.upf464.koonsdiary.domain.repository.NotificationRepository
import javax.inject.Inject

class FetchNotificationListUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    suspend operator fun invoke(): Result<Response> {
        return notificationRepository.fetchNotificationList().map { notificationList ->
            Response(notificationList)
        }
    }

    data class Response(
        val notificationList: List<Notification>
    )
}
