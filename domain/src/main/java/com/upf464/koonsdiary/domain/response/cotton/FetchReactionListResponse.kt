package com.upf464.koonsdiary.domain.response.cotton

import com.upf464.koonsdiary.domain.model.Reaction
import com.upf464.koonsdiary.domain.response.Response

data class FetchReactionListResponse(
    val reactionList: List<Reaction>
) : Response
