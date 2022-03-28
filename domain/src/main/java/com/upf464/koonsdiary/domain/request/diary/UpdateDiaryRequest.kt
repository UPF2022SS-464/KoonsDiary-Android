package com.upf464.koonsdiary.domain.request.diary

import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.request.Request
import java.time.LocalDate

data class UpdateDiaryRequest(
    val diaryId: Int,
    val date: LocalDate,
    val content: String,
    val sentiment: Sentiment,
    val imageList: List<Diary.Image>
) : Request