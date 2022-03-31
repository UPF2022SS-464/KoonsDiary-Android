package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.request.Request

data class FetchCommentListRequest(
    val diaryId: Int
) : Request