package com.upf464.koonsdiary.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Diary(
    val id: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val content: String = "",
    val sentiment: Sentiment = Sentiment.VERY_SAD,
    val imageList: List<Image> = emptyList(),
    val lastModifiedDate: LocalDateTime = LocalDateTime.now(),
    val createdDate: LocalDateTime = LocalDateTime.now()
) {

    data class Image(
        val imagePath: String,
        val comment: String
    )
}