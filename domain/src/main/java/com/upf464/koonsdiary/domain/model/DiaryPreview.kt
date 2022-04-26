package com.upf464.koonsdiary.domain.model

import java.time.LocalDate

data class DiaryPreview(
    val id: Int,
    val date: LocalDate,
    val content: String,
    val imagePath: String?
)
