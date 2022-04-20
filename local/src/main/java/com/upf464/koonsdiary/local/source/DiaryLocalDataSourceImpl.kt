package com.upf464.koonsdiary.local.source

import com.upf464.koonsdiary.data.source.DiaryLocalDataSource
import com.upf464.koonsdiary.local.dao.DiarySentimentDao
import com.upf464.koonsdiary.local.model.DateDto
import com.upf464.koonsdiary.local.model.DiarySentimentDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

internal class DiaryLocalDataSourceImpl @Inject constructor(
    private val sentimentDao: DiarySentimentDao
) : DiaryLocalDataSource {

    override suspend fun saveMonthlySentiment(
        year: Int,
        month: Int,
        sentimentList: List<Int?>
    ): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            sentimentDao.delete(year, month)
            val dtoList = sentimentList.withIndex()
                .filter { it.value != null }
                .map { (day, sentiment) ->
                    DiarySentimentDto(date = DateDto(year, month, day + 1), sentiment!!)
                }

            sentimentDao.insert(dtoList)
        }
    }

    override suspend fun fetchMonthlySentiment(
        year: Int,
        month: Int
    ): Result<List<Int?>> = runCatching {
        withContext(Dispatchers.IO) {
            val date = LocalDate.of(year, month, 1)
            val sentimentMap = sentimentDao.fetch(year, month).associate { dto ->
                Pair(
                    dto.date,
                    dto.sentiment
                )
            }
            (1..date.lengthOfMonth()).map { day ->
                sentimentMap[DateDto(year, month, day)]
            }
        }
    }
}
