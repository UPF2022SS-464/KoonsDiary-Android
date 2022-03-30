package com.upf464.koonsdiary.data.model

import java.time.LocalDateTime

data class QuestionAnswerData(
    val id: Int = 0,
    val writerId: Int = 0,
    val date: LocalDateTime = LocalDateTime.now(),
    val questionId: String,
    val content: String
)
