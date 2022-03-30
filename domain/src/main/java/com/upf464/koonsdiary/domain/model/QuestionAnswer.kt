package com.upf464.koonsdiary.domain.model

import java.time.LocalDateTime

data class QuestionAnswer(
    val id: Int = 0,
    val writerId: Int = 0,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val questionId: Int,
    val content: String
)
