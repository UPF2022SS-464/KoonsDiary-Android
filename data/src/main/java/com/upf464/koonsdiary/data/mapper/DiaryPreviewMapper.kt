package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.DiaryPreviewData
import com.upf464.koonsdiary.domain.model.DiaryPreview

internal fun DiaryPreviewData.toDomain() = DiaryPreview(
    id = id,
    content = content,
    imagePath = imagePath
)