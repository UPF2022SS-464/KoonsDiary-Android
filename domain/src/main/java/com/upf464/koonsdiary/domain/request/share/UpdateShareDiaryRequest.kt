package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.domain.request.Request

data class UpdateShareDiaryRequest(
    val diaryId: Int,
    val content: String,
    val imageList: List<DiaryImage>
) : Request