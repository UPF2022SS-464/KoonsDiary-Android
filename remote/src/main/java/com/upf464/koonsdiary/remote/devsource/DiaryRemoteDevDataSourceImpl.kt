package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.DiaryData
import com.upf464.koonsdiary.data.model.DiaryPreviewData
import com.upf464.koonsdiary.data.source.DiaryRemoteDataSource
import java.time.LocalDate
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

    override suspend fun fetchDiary(diaryId: Int): Result<DiaryData> {
        return Result.success(DiaryData())
    }

    override suspend fun fetchDiaryPreview(diaryId: Int): Result<DiaryPreviewData> {
        return Result.success(DiaryPreviewData(
            id = 1,
            content = "",
            imagePath = ""
        ))
    }

    override suspend fun fetchMonthlySentiment(year: Int, month: Int): Result<List<Int>> {
        val lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth()
        val sentimentList = (1..lengthOfMonth).map { it % 5 }
        return Result.success(sentimentList)
    }
}