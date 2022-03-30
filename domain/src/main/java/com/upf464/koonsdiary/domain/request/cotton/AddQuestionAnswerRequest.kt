package com.upf464.koonsdiary.domain.request.cotton

import com.upf464.koonsdiary.domain.request.Request

data class AddQuestionAnswerRequest(
    val questionId: Int,
    val content: String
) : Request