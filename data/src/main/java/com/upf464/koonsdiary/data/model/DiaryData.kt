package com.upf464.koonsdiary.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class DiaryData(
    val id: Int = 0,
    val date: LocalDate,
    val content: String,
    val sentiment: Int,
    val imageList: List<DiaryImageData>,
    val lastModifiedDate: LocalDateTime = LocalDateTime.now(),
    val createdDate: LocalDateTime = LocalDateTime.now()
)
