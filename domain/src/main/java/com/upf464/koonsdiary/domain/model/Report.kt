package com.upf464.koonsdiary.domain.model

data class Report(
    val dateTerm: String = "",
    val period: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val sentimentGraph: List<SentimentTracking>,
    val sentimentDistribution: Map<Int, Sentiment>,
    val koonsMention: Map<String, Sentiment>
)
