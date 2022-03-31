package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.domain.request.Request

data class AddShareDiaryRequest(
    val groupId: Int,
    val content: String,
    val imageList: List<DiaryImage>
) : Request