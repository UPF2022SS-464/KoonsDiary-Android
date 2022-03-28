package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.GetRandomQuestionRequest
import com.upf464.koonsdiary.domain.response.cotton.GetRandomQuestionResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class GetRandomQuestionUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) : ResultUseCase<GetRandomQuestionRequest, GetRandomQuestionResponse> {

    override suspend fun invoke(request: GetRandomQuestionRequest): Result<GetRandomQuestionResponse> {
        return cottonRepository.getRandomQuestion().map { question ->
            GetRandomQuestionResponse(question)
        }
    }
}