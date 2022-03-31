package com.upf464.koonsdiary.domain.request.diary

import com.upf464.koonsdiary.domain.model.DiaryImage
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.request.Request
import java.time.LocalDate

data class AddDiaryRequest(
    val date: LocalDate,
    val content: String,
    val sentiment: Sentiment,
    val imageList: List<DiaryImage>
) : Request
