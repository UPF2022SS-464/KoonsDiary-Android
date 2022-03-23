package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.data.source.DiaryRemoteDataSource
import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import javax.inject.Inject

internal class DiaryRepositoryImpl @Inject constructor(
    private val remote: DiaryRemoteDataSource
) : DiaryRepository {

    override fun fetchSentimentOf(content: String): Result<Sentiment> {
        return remote.fetchSentimentOf(content).flatMap { sentimentOrder ->
            Result.success(Sentiment.values()[sentimentOrder])
        }
    }
}