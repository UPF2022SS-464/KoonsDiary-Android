package com.upf464.koonsdiary.data.model

import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.model.SentimentTracking

data class ReportData(
    val dateTerm: String = "",
    val period: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val sentimentGraph: List<SentimentTracking>,
    val sentimentDistribution: Map<Int, Sentiment>,
    val koonsMention: Map<String, Sentiment>
)
