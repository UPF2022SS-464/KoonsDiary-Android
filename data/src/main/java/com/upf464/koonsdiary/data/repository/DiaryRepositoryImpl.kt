package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.mapper.toData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.DiaryLocalDataSource
import com.upf464.koonsdiary.data.source.DiaryRemoteDataSource
import com.upf464.koonsdiary.domain.error.CacheError
import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.model.DiaryPreview
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DiaryRepositoryImpl @Inject constructor(
    private val remote: DiaryRemoteDataSource,
    private val local: DiaryLocalDataSource
) : DiaryRepository {

    override suspend fun fetchSentimentOf(content: String): Result<Sentiment> {
        return remote.fetchSentimentOf(content).map { sentimentOrder ->
            Sentiment.values()[sentimentOrder]
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun addDiary(diary: Diary): Result<Int> {
        return remote.addDiary(diary.toData()).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun updateDiary(diary: Diary): Result<Int> {
        return remote.updateDiary(diary.toData()).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun deleteDiary(diaryId: Int): Result<Unit> {
        return remote.deleteDiary(diaryId).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun fetchDiary(diaryId: Int): Result<Diary> {
        return remote.fetchDiary(diaryId).map { diary ->
            diary.toDomain()
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun fetchDiaryPreview(diaryId: Int): Result<DiaryPreview> {
        return remote.fetchDiaryPreview(diaryId).map { diaryPreview ->
            diaryPreview.toDomain()
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override fun fetchMonthlySentimentFlow(year: Int, month: Int): Flow<Result<List<Sentiment?>>> {
        return flow {
            val localResult = local.fetchMonthlySentiment(year, month)
                .map { sentimentList ->
                    sentimentList.map { sentiment ->
                        sentiment?.let { Sentiment.values()[it] }
                    }
                }.errorMap { error ->
                    CacheError.Diary(null, error)
                }
            emit(localResult)

            val remoteResult = remote.fetchMonthlySentiment(year, month)
                .onSuccess { sentimentList ->
                    local.saveMonthlySentiment(year, month, sentimentList)
                }.map { sentimentList ->
                    sentimentList.map { sentiment ->
                        sentiment?.let { Sentiment.values()[it] }
                    }
                }.errorMap { error ->
                    if (error is ErrorData) error.toDomain()
                    else Exception(error)
                }
            emit(remoteResult)
        }
    }
}