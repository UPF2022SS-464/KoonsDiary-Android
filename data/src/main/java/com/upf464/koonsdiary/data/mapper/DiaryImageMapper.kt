package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.DiaryImageData
import com.upf464.koonsdiary.domain.model.DiaryImage

internal fun DiaryImage.toData() = DiaryImageData(
    imagePath = imagePath,
    comment = comment
)

internal fun DiaryImageData.toDomain() = DiaryImage(
    imagePath = imagePath,
    comment = comment
)
