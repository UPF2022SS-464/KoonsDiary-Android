package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.error.CottonAnswerError
import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.AddQuestionAnswerRequest
import com.upf464.koonsdiary.domain.response.cotton.AddQuestionAnswerResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AddQuestionAnswerUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) : ResultUseCase<AddQuestionAnswerRequest, AddQuestionAnswerResponse> {

    override suspend fun invoke(request: AddQuestionAnswerRequest): Result<AddQuestionAnswerResponse> {
        if (request.content.isBlank()) {
            return Result.failure(CottonAnswerError.EmptyContent)
        }

        val questionAnswer = QuestionAnswer(
            writerId = request.writerId,
            questionId = request.questionId,
            content = request.content
        )

        return cottonRepository.addQuestionAnswer(questionAnswer).map { answerId ->
            AddQuestionAnswerResponse(answerId)
        }
    }
}