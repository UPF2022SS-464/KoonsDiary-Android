package com.upf464.koonsdiary.presentation.model.diary.calendar

import java.time.LocalDate

data class PreviewModel(
    val diaryId: Int,
    val date: LocalDate,
    val content: String,
    val imagePath: String?
)
