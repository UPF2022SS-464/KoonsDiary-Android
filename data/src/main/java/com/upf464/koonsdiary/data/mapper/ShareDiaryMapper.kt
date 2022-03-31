package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.ShareDiaryData
import com.upf464.koonsdiary.domain.model.ShareDiary

internal fun ShareDiaryData.toDomain() = ShareDiary(
    id = id,
    group = group.toDomain(),
    user = user.toDomain(),
    content = content,
    imageList = imageList.map { it.toDomain() },
    createdDate = createdDate,
    lastModifiedDate = lastModifiedDate
)

internal fun ShareDiary.toData() = ShareDiaryData(
    id = id,
    group = group.toData(),
    user = user.toData(),
    content = content,
    imageList = imageList.map { it.toData() },
    createdDate = createdDate,
    lastModifiedDate = lastModifiedDate
)