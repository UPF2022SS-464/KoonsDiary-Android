package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.error.CottonError
import com.upf464.koonsdiary.domain.model.Question
import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.repository.CottonRepository
import javax.inject.Inject

class AddQuestionAnswerUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {
        if (request.content.isBlank()) {
            return Result.failure(CottonError.EmptyContent)
        }

        val questionAnswer = QuestionAnswer(
            question = Question(id = request.questionId),
            content = request.content
        )

        return cottonRepository.addQuestionAnswer(questionAnswer).map { answerId ->
            Response(answerId)
        }
    }

    data class Request(
        val questionId: Int,
        val content: String
    )

    data class Response(
        val answerId: Int
    )
}
