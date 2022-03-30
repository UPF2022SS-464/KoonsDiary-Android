package com.upf464.koonsdiary.domain.request.cotton

import com.upf464.koonsdiary.domain.request.Request

data class AddQuestionAnswerRequest(
    val writerId: Int,
    val questionId: Int,
    val content: String
) : Request