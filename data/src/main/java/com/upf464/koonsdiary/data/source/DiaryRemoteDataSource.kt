package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.DiaryData
import com.upf464.koonsdiary.data.model.DiaryPreviewData

interface DiaryRemoteDataSource {

    suspend fun fetchSentimentOf(content: String): Result<Int>

    suspend fun addDiary(diary: DiaryData): Result<Int>

    suspend fun updateDiary(diary: DiaryData): Result<Int>

    suspend fun deleteDiary(diaryId: Int): Result<Unit>

    suspend fun fetchDiary(diaryId: Int): Result<DiaryData>

    suspend fun fetchDiaryPreview(diaryId: Int): Result<DiaryPreviewData>

    suspend fun fetchMonthlySentiment(year: Int, month: Int): Result<List<Int?>>
}