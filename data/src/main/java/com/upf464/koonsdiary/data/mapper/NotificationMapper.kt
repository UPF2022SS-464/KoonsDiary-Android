package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.NotificationData
import com.upf464.koonsdiary.domain.model.Notification

internal fun NotificationData.toDomain() = when (this) {
    is NotificationData.GroupInvite -> Notification.GroupInvite(
        group = group.toDomain(),
        isAccepted = isAccepted,
    )
    is NotificationData.CottonReaction -> Notification.CottonReaction(
        answer = answer.toDomain(),
        reaction = reaction.toDomain(),
    )
    is NotificationData.NewDiary -> Notification.NewDiary(
        diary = diary.toDomain(),
    )
    is NotificationData.DiaryComment -> Notification.DiaryComment(
        diary = diary.toDomain(),
        comment = comment.toDomain(),
    )
}
