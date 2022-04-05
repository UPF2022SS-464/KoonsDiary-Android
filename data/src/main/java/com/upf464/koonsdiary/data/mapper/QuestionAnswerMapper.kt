package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.QuestionAnswerData
import com.upf464.koonsdiary.domain.model.QuestionAnswer

internal fun QuestionAnswer.toData() = QuestionAnswerData(
    id = id,
    writerId = writerId,
    createdDate = createdDate,
    questionId = questionId.toData(),
    content = content,
    reactionList = reactionList
)

internal fun QuestionAnswerData.toDomain() = QuestionAnswer(
    id = id,
    writerId = writerId,
    createdDate = createdDate,
    questionId = questionId.toDomain(),
    content = content,
    reactionList = reactionList
)