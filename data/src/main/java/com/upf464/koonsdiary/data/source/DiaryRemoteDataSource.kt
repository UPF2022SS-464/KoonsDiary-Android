package com.upf464.koonsdiary.data.source

interface DiaryRemoteDataSource {

    suspend fun fetchSentimentOf(content: String): Result<Int>
}