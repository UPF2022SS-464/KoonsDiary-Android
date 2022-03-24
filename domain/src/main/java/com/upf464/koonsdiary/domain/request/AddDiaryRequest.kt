package com.upf464.koonsdiary.domain.request

import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.model.Sentiment
import java.time.LocalDate

data class AddDiaryRequest(
    val date: LocalDate,
    val content: String,
    val sentiment: Sentiment,
    val imageList: List<Diary.Image>
) : Request
