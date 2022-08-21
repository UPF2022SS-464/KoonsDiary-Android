package com.upf464.koonsdiary.domain.model

import java.time.LocalDate

data class DiaryPreview(
    val id: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val content: String = "",
    val imagePath: String? = null,
)
