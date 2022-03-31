package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.DiaryData
import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.model.Sentiment

internal fun Diary.toData() = DiaryData(
    id = id,
    date = date,
    content = content,
    sentiment = sentiment.ordinal,
    imageList = imageList.map { it.toData() },
    lastModifiedDate = lastModifiedDate,
    createdDate = createdDate
)

internal fun DiaryData.toDomain() = Diary(
    id = id,
    date = date,
    content = content,
    sentiment = Sentiment.values()[sentiment],
    imageList = imageList.map { it.toDomain() },
    lastModifiedDate = lastModifiedDate,
    createdDate = createdDate
)