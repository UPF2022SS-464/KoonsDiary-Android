package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.FetchRandomQuestionRequest
import com.upf464.koonsdiary.domain.response.cotton.FetchRandomQuestionResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchRandomQuestionUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) : ResultUseCase<FetchRandomQuestionRequest, FetchRandomQuestionResponse> {

    override suspend fun invoke(request: FetchRandomQuestionRequest): Result<FetchRandomQuestionResponse> {
        return cottonRepository.fetchRandomQuestion().map { question ->
            FetchRandomQuestionResponse(question)
        }
    }
}