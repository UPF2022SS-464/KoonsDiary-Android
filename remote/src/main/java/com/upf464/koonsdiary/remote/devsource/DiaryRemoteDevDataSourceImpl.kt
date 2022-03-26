package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.DiaryData
import com.upf464.koonsdiary.data.source.DiaryRemoteDataSource
import javax.inject.Inject

internal class DiaryRemoteDevDataSourceImpl @Inject constructor(
) : DiaryRemoteDataSource {

    override suspend fun fetchSentimentOf(content: String): Result<Int> {
        return Result.success(0)
    }

    override suspend fun addDiary(diary: DiaryData): Result<Int> {
        return Result.success(0)
    }

    override suspend fun updateDiary(diary: DiaryData): Result<Int> {
        return Result.success(0)
    }

    override suspend fun deleteDiary(diaryId: Int): Result<Unit> {
        return Result.success(Unit)
    }
}