package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.FetchReactionRequest
import com.upf464.koonsdiary.domain.response.cotton.FetchReactionResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchReactionUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) : ResultUseCase<FetchReactionRequest, FetchReactionResponse> {

    override suspend fun invoke(request: FetchReactionRequest): Result<FetchReactionResponse> {
        return cottonRepository.fetchReaction().map { reaction ->
            FetchReactionResponse(reaction)
        }
    }
}