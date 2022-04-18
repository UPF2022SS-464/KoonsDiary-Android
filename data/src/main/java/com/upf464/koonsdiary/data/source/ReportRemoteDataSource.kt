package com.upf464.koonsdiary.data.source

import java.time.LocalDate

interface ReportRemoteDataSource {

    suspend fun fetchAllSentiment(refresh: Boolean = false): Result<Map<LocalDate, Int>>

    suspend fun fetchKoonsMention(sentiment: Int): Result<String>
}