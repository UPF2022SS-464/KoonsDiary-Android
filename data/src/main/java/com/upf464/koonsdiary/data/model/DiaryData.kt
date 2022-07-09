package com.upf464.koonsdiary.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class DiaryData(
    val id: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val content: String = "",
    val sentiment: Int = 0,
    val imageList: List<DiaryImageData> = listOf(),
    val lastModifiedDate: LocalDateTime = LocalDateTime.now(),
    val createdDate: LocalDateTime = LocalDateTime.now()
)
