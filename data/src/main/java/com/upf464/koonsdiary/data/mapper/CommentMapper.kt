package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.CommentData
import com.upf464.koonsdiary.domain.model.Comment

internal fun Comment.toData() = CommentData(
    id = id,
    diaryId = diaryId,
    user = user.toData(),
    content = content,
    createdDate = createdDate
)

internal fun CommentData.toDomain() = Comment(
    id = id,
    diaryId = diaryId,
    user = user.toDomain(),
    content = content,
    createdDate = createdDate
)
