package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.model.Question
import com.upf464.koonsdiary.domain.repository.CottonRepository
import javax.inject.Inject

class FetchRandomQuestionUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) {

    suspend operator fun invoke(): Result<Response> {
        return cottonRepository.fetchRandomQuestion().map { question ->
            Response(question)
        }
    }

    data class Response(
        val question: Question
    )
}
