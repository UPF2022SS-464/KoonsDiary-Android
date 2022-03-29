package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.error.CottonAnswerError
import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.AddCottonAnswerRequest
import com.upf464.koonsdiary.domain.response.cotton.AddCottonAnswerResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AddCottonAnswerUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) : ResultUseCase<AddCottonAnswerRequest, AddCottonAnswerResponse> {

    override suspend fun invoke(request: AddCottonAnswerRequest): Result<AddCottonAnswerResponse> {
        if (request.content.isBlank()) {
            return Result.failure(CottonAnswerError.EmptyContent)
        }

        val questionAnswer = QuestionAnswer(
            writer = request.writer,
            date = request.date,
            questionId = request.questionId,
            content = request.content
        )

        return cottonRepository.addCottonAnswer(questionAnswer).map { id ->
            AddCottonAnswerResponse(id)
        }
    }
}