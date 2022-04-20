package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.QuestionData
import com.upf464.koonsdiary.domain.model.Question

internal fun Question.toData() = QuestionData(
    id = id,
    korean = korean,
    english = english
)

internal fun QuestionData.toDomain() = Question(
    id = id,
    korean = korean,
    english = english
)
