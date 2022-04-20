package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.common.DiaryValidator
import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import javax.inject.Inject

class AnalyzeSentimentUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val validator: DiaryValidator
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        if (!validator.validateContent(request.content)) {
            return Result.failure(DiaryError.EmptyContent)
        }

        return diaryRepository.fetchSentimentOf(request.content).map { sentiment ->
            Response(sentiment)
        }
    }

    data class Request(
        val content: String
    )

    data class Response(
        val sentiment: Sentiment
    )
}
