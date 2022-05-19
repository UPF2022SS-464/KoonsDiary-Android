package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.DiaryData
import com.upf464.koonsdiary.data.model.DiaryPreviewData
import com.upf464.koonsdiary.data.source.DiaryRemoteDataSource
import java.time.LocalDate
import javax.inject.Inject

internal class DiaryRemoteDevDataSourceImpl @Inject constructor() : DiaryRemoteDataSource {

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
        return Result.success(
            DiaryData(
                date = LocalDate.of(2022, 3, 30),
                content = "content",
                sentiment = 1,
                imageList = emptyList()
            )
        )
    }

    override suspend fun fetchDiaryPreview(date: LocalDate): Result<DiaryPreviewData> {
        return Result.success(
            DiaryPreviewData(
                id = 1,
                date = date,
                content = "엄청나게 긴 글인데 이걸 어디까지 써야되나 복붙이나 하자 ㅎㅎㅎ 엄청나게 긴 글인데 이걸 어디까지 써야되나 복붙이나 하자 ㅎㅎㅎ 엄청나게 긴 글인데 이걸 어디까지 써야되나 복붙이나 하자 ㅎㅎㅎ",
                imagePath = "https://developers.kakao.com/tool/resource/static/img/button/login/full/ko/kakao_login_large_wide.png"
            )
        )
    }

    override suspend fun fetchMonthlySentiment(year: Int, month: Int): Result<List<Int>> {
        val lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth()
        val sentimentList = (1..lengthOfMonth).map { it % 5 }
        return Result.success(sentimentList)
    }
}
