package com.upf464.koonsdiary.data.model

import java.time.LocalDate

data class DiaryPreviewData(
    val id: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val content: String = "",
    val imagePath: String? = null,
)
