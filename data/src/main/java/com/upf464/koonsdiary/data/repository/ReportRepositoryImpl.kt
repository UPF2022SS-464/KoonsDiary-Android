package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.ReportRemoteDataSource
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.ReportRepository
import java.time.LocalDate
import javax.inject.Inject

internal class ReportRepositoryImpl @Inject constructor(
    private val remote: ReportRemoteDataSource,
) : ReportRepository {

    private var sentimentMap: Map<LocalDate, Sentiment>? = null

    override suspend fun fetchAllSentiment(refresh: Boolean): Result<Map<LocalDate, Sentiment>> {
        if (!refresh) {
            sentimentMap?.let { sentimentMap ->
                return Result.success(sentimentMap)
            }
        }

        return remote.fetchAllSentiment().map { sentimentMap ->
            sentimentMap.map { (date, sentiment) ->
                date to Sentiment.values()[sentiment]
            }.toMap()
        }.onSuccess {
            sentimentMap = it
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun fetchKoonsMention(mostSentimentSet: Set<Sentiment>): Result<String> {
        return remote.fetchKoonsMention(mostSentimentSet.map { it.ordinal }.toSet())
            .errorMap { error ->
                if (error is ErrorData) error.toDomain()
                else Exception(error)
            }
    }
}
