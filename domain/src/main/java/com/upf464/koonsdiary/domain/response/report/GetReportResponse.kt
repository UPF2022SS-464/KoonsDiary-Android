package com.upf464.koonsdiary.domain.response.report

import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.response.Response

data class GetReportResponse(
    val GraphList: List<Double?> = emptyList(),
    val sentimentPercentageMap: Map<Sentiment, Int> = emptyMap(),
    val mostSentiment: Sentiment,
    val koonsMention: String
) : Response