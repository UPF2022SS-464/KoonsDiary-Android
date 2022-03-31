package com.upf464.koonsdiary.domain.request.share

import com.upf464.koonsdiary.domain.request.Request

data class DeleteCommentRequest(
    val commentId: Int
) : Request