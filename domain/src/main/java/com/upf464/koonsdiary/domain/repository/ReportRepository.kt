package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.Sentiment
import java.time.LocalDate

interface ReportRepository {

    suspend fun fetchAllSentiment(refresh: Boolean = false): Result<Map<LocalDate, Sentiment>>

    suspend fun fetchKoonsMention(sentiment: Sentiment): Result<String>
}