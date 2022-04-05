package com.upf464.koonsdiary.domain.request.cotton

import com.upf464.koonsdiary.domain.request.Request

data class FetchRandomAnswerRequest(
    val answerId: Int
) : Request