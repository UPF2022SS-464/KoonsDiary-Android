package com.upf464.koonsdiary.data.model

import com.upf464.koonsdiary.domain.model.Question
import java.time.LocalDateTime

data class QuestionAnswerData(
    val id: Int = 0,
    val writerId: Int = 0,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val questionId: Question,
    val content: String
)
