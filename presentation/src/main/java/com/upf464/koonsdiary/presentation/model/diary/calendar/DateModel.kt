package com.upf464.koonsdiary.presentation.model.diary.calendar

import com.upf464.koonsdiary.domain.model.Sentiment
import java.time.LocalDate

data class DateModel(
    val date: LocalDate,
    val sentiment: Sentiment?
)
