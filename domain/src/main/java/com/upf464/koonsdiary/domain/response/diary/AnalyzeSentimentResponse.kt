package com.upf464.koonsdiary.domain.response.diary

import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.response.Response

data class AnalyzeSentimentResponse(
    val sentiment: Sentiment
) : Response
