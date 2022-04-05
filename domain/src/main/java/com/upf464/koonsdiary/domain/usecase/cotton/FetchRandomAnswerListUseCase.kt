package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.FetchRandomAnswerListRequest
import com.upf464.koonsdiary.domain.response.cotton.FetchRandomAnswerListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchRandomAnswerListUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) : ResultUseCase<FetchRandomAnswerListRequest, FetchRandomAnswerListResponse> {

    override suspend fun invoke(request: FetchRandomAnswerListRequest): Result<FetchRandomAnswerListResponse> {
        return cottonRepository.fetchRandomAnswerList().map { questionAnswer ->
            FetchRandomAnswerListResponse(questionAnswer)
        }
    }
}