package com.upf464.koonsdiary.domain.model

data class QuestionAnswer(
    val id: Int,
    val writer: String,
    val date: String,
    val questionId: String,
    val content: String
)
