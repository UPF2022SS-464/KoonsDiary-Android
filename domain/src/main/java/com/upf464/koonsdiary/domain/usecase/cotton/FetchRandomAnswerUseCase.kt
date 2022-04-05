package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.FetchRandomAnswerRequest
import com.upf464.koonsdiary.domain.response.cotton.FetchRandomAnswerResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchRandomAnswerUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) : ResultUseCase<FetchRandomAnswerRequest, FetchRandomAnswerResponse> {

    override suspend fun invoke(request: FetchRandomAnswerRequest): Result<FetchRandomAnswerResponse> {
        return cottonRepository.fetchRandomAnswer().map { questionAnswer ->
            FetchRandomAnswerResponse(questionAnswer)
        }
    }
}