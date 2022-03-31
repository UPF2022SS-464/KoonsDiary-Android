package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.request.Request

data class AddCommentRequest(
    val diaryId: Int,
    val content: String
) : Request