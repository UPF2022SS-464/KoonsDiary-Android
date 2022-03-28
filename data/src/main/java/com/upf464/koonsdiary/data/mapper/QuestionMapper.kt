package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.model.QuestionData
import com.upf464.koonsdiary.domain.model.Question

internal fun Question.toData() = QuestionData(
    id = id,
    questionKR = questionKR,
    questionUS = questionUS
)

internal fun QuestionData.toDT() = Question(
    id = id,
    questionKR = questionKR,
    questionUS = questionUS
)