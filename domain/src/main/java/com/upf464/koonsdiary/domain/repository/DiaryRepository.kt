package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.model.DiaryPreview
import com.upf464.koonsdiary.domain.model.Sentiment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DiaryRepository {

    suspend fun fetchSentimentOf(content: String): Result<Sentiment>

    suspend fun addDiary(diary: Diary): Result<Int>

    suspend fun updateDiary(diary: Diary): Result<Int>

    suspend fun deleteDiary(diaryId: Int): Result<Unit>

    suspend fun fetchDiary(diaryId: Int): Result<Diary>

    suspend fun fetchDiaryPreview(date: LocalDate): Result<DiaryPreview>

    fun fetchMonthlySentimentFlow(year: Int, month: Int): Flow<Result<List<Sentiment?>>>
}
