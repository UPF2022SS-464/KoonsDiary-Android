package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.source.ReportRemoteDataSource
import java.time.LocalDate
import javax.inject.Inject

internal class ReportRemoteDevDataSourceImpl @Inject constructor(
) : ReportRemoteDataSource {

    override suspend fun fetchAllSentiment(refresh: Boolean): Result<Map<LocalDate, Int>> {
        return Result.success(emptyMap())
    }

    override suspend fun fetchKoonsMention(sentiment: Int): Result<String> {
        return Result.success("")
    }
}