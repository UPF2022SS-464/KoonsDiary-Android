package com.upf464.koonsdiary.domain.request.cotton

import com.upf464.koonsdiary.domain.request.Request
import java.time.LocalDate

data class AddCottonAnswerRequest(
    val writer: String,
    val date: LocalDate,
    val questionId: String,
    val content: String
) : Request