package com.upf464.koonsdiary.domain.response.cotton

import com.upf464.koonsdiary.domain.model.Reaction
import com.upf464.koonsdiary.domain.response.Response

data class FetchReactionResponse(
    val reaction: Reaction
) : Response
