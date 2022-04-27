package com.upf464.koonsdiary.presentation.model.diary.detail

import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.domain.model.Sentiment
import java.time.LocalDate

data class DiaryDetailModel(
    val id: Int = 0,
    val date: LocalDate,
    val content: String,
    val sentiment: Sentiment,
    val imageList: List<DiaryImage>
)
