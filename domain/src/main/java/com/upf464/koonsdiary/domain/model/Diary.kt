package com.upf464.koonsdiary.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Diary(
    val id: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val content: String = "",
    val sentiment: Sentiment = Sentiment.NORMAL,
    val imageList: List<DiaryImage> = listOf(),
    val lastModifiedDate: LocalDateTime = LocalDateTime.now(),
    val createdDate: LocalDateTime = LocalDateTime.now()
)
