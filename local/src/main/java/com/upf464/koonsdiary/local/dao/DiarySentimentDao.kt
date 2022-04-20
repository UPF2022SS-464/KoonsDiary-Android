package com.upf464.koonsdiary.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upf464.koonsdiary.local.model.DiarySentimentDto

@Dao
interface DiarySentimentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sentimentList: List<DiarySentimentDto>)

    @Query("SELECT * FROM DiarySentiment WHERE date_year=:year AND date_month=:month")
    suspend fun fetch(year: Int, month: Int): List<DiarySentimentDto>

    @Query("DELETE FROM DiarySentiment WHERE date_year=:year AND date_month=:month")
    suspend fun delete(year: Int, month: Int)
}
