package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.common.DiaryValidator
import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.AnalyzeSentimentRequest
import com.upf464.koonsdiary.domain.response.AnalyzeSentimentResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AnalyzeSentimentUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val validator: DiaryValidator
) : ResultUseCase<AnalyzeSentimentRequest, AnalyzeSentimentResponse> {

    override suspend fun invoke(request: AnalyzeSentimentRequest): Result<AnalyzeSentimentResponse> {
        if (!validator.validateContent(request.content)) {
            return Result.failure(DiaryError.EmptyContent)
        }

        return diaryRepository.fetchSentimentOf(request.content).flatMap { sentiment ->
            Result.success(AnalyzeSentimentResponse(sentiment))
        }
    }
}