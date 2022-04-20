package com.upf464.koonsdiary.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Diary(
    val id: Int = 0,
    val date: LocalDate,
    val content: String,
    val sentiment: Sentiment,
    val imageList: List<DiaryImage>,
    val lastModifiedDate: LocalDateTime = LocalDateTime.now(),
    val createdDate: LocalDateTime = LocalDateTime.now()
)
