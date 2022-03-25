package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.model.Sentiment

interface DiaryRepository {

    suspend fun fetchSentimentOf(content: String): Result<Sentiment>

    suspend fun addDiary(diary: Diary): Result<Int>

    suspend fun updateDiary(diary: Diary): Result<Int>
}