package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.DiaryData

interface DiaryRemoteDataSource {

    suspend fun fetchSentimentOf(content: String): Result<Int>

    suspend fun addDiary(diary: DiaryData): Result<Int>
}