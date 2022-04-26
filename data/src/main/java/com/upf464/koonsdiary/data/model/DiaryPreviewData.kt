package com.upf464.koonsdiary.data.model

import java.time.LocalDate

data class DiaryPreviewData(
    val id: Int,
    val date: LocalDate,
    val content: String,
    val imagePath: String?
)
