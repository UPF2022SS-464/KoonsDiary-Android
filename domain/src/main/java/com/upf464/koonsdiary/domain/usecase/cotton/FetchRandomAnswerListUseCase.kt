package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.model.QuestionAnswer
import com.upf464.koonsdiary.domain.repository.CottonRepository
import javax.inject.Inject

class FetchRandomAnswerListUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) {

    suspend operator fun invoke(): Result<Response> {
        return cottonRepository.fetchRandomAnswerList().map { questionAnswer ->
            Response(questionAnswer)
        }
    }

    data class Response(
        val questionAnswerList: List<QuestionAnswer>
    )
}
