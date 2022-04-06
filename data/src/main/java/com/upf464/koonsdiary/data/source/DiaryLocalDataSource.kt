package com.upf464.koonsdiary.data.source

interface DiaryLocalDataSource {

    suspend fun saveMonthlySentiment(
        year: Int,
        month: Int,
        sentimentList: List<Int?>
    ): Result<Unit>

    suspend fun fetchMonthlySentiment(year: Int, month: Int): Result<List<Int?>>
}