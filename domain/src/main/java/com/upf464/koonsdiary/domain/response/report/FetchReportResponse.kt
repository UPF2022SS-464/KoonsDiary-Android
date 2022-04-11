package com.upf464.koonsdiary.domain.response.report

import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.response.Response

data class FetchReportResponse(
    val graphMap: Map<Int, Sentiment>,
    val SentimentList: Map<Int, Sentiment>,
    val koonsComment: String
) : Response