package com.upf464.koonsdiary.data.source

import java.time.LocalDate

interface ReportRemoteDataSource {

    suspend fun fetchAllSentiment(): Result<Map<LocalDate, Int>>

    suspend fun fetchKoonsMention(sentimentSet: Set<Int>): Result<String>
}
