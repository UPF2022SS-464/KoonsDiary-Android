package com.upf464.koonsdiary.data.source

interface DiaryRemoteDataSource {

    fun fetchSentimentOf(content: String): Result<Int>
}