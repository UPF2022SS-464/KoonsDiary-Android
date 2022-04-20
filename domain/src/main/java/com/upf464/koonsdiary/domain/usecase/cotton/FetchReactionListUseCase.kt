package com.upf464.koonsdiary.domain.usecase.cotton

import com.upf464.koonsdiary.domain.model.Reaction
import com.upf464.koonsdiary.domain.repository.CottonRepository
import javax.inject.Inject

class FetchReactionListUseCase @Inject constructor(
    private val cottonRepository: CottonRepository
) {

    suspend operator fun invoke(): Result<Response> {
        return cottonRepository.fetchReactionList().map { reactionList ->
            Response(reactionList)
        }
    }

    data class Response(
        val reactionList: List<Reaction>
    )
}
