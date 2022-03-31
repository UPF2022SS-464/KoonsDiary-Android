package com.upf464.koonsdiary.domain.response.share

import com.upf464.koonsdiary.domain.model.Comment
import com.upf464.koonsdiary.domain.response.Response

data class FetchCommentListResponse(
    val commentList: List<Comment>
) : Response