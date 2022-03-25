package com.upf464.koonsdiary.domain.model

data class QuestionAnswer(
    val id: String,
    val writer: String,
    val date: String,
    val FKquestion : String, //FK
    val content: String
)
