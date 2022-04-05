package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.request.cotton.FetchReactionListRequest
import com.upf464.koonsdiary.domain.response.cotton.FetchReactionListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchReactionListUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) : ResultUseCase<FetchReactionListRequest, FetchReactionListResponse> {

    override suspend fun invoke(request: FetchReactionListRequest): Result<FetchReactionListResponse> {
        return cottonRepository.fetchReactionList().map { reactionList ->
            FetchReactionListResponse(reactionList)
        }
    }
}