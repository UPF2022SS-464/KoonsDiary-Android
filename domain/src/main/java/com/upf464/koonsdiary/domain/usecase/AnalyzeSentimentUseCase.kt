package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.AnalyzeSentimentRequest
import com.upf464.koonsdiary.domain.response.AnalyzeSentimentResponse
import javax.inject.Inject

internal class AnalyzeSentimentUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ResultUseCase<AnalyzeSentimentRequest, AnalyzeSentimentResponse> {

    override suspend fun invoke(request: AnalyzeSentimentRequest): Result<AnalyzeSentimentResponse> {
        return diaryRepository.fetchSentimentOf(request.content).flatMap { sentiment ->
            Result.success(AnalyzeSentimentResponse(sentiment))
        }
    }
}