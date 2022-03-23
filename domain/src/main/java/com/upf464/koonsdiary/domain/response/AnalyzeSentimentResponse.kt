package com.upf464.koonsdiary.domain.response

import com.upf464.koonsdiary.domain.model.Sentiment

data class AnalyzeSentimentResponse(
    val sentiment: Sentiment
) : Response
