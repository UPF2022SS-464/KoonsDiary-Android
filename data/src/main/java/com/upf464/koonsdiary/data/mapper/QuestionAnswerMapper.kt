package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.QuestionAnswerData
import com.upf464.koonsdiary.domain.model.QuestionAnswer

internal fun QuestionAnswer.toData() = QuestionAnswerData(
    id = id,
    writerId = writerId,
    createdDate = createdDate,
    question = question.toData(),
    content = content,
    reactionMap = reactionMap
)

internal fun QuestionAnswerData.toDomain() = QuestionAnswer(
    id = id,
    writerId = writerId,
    createdDate = createdDate,
    question = question.toDomain(),
    content = content,
    reactionMap = reactionMap
)
