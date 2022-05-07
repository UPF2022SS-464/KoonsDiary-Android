package com.upf464.koonsdiary.presentation.mapper

import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryImageModel

internal fun DiaryImageModel.toDomain() = DiaryImage(
    imagePath = imagePath,
    comment = content.value
)
