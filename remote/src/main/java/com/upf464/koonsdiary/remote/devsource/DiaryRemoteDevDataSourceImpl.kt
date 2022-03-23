package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.source.DiaryRemoteDataSource
import javax.inject.Inject

internal class DiaryRemoteDevDataSourceImpl @Inject constructor(
) : DiaryRemoteDataSource {

    override suspend fun fetchSentimentOf(content: String): Result<Int> {
        return Result.success(0)
    }
}