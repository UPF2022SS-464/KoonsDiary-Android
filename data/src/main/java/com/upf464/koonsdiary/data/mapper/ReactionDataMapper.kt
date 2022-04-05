package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.ReactionData
import com.upf464.koonsdiary.domain.model.Reaction

internal fun Reaction.toData() = ReactionData(
    id = id,
    name = name
)
