package com.upf464.koonsdiary.domain.response.cotton

import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.response.Response

data class FetchRandomAnswerResponse(
    val questionAnswer: List<QuestionAnswer>
) : Response