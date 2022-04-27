package com.upf464.koonsdiary.presentation.mapper

import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryDetailModel

internal fun Diary.toDetailModel() = DiaryDetailModel(
    id = id,
    date = date,
    content = content,
    sentiment = sentiment,
    imageList = imageList
)
