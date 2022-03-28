package com.upf464.koonsdiary.domain.response.cotton

import com.upf464.koonsdiary.domain.model.Question
import com.upf464.koonsdiary.domain.response.Response

data class FetchRandomQuestionResponse(
    val question: Question
) : Response