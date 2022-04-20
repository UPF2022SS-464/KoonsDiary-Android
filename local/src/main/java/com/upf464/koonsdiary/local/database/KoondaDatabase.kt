package com.upf464.koonsdiary.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.upf464.koonsdiary.local.dao.DiarySentimentDao
import com.upf464.koonsdiary.local.model.DiarySentimentDto

@Database(
    entities = [
        DiarySentimentDto::class
    ],
    version = 1
)
internal abstract class KoondaDatabase : RoomDatabase() {

    abstract fun getDiaryDao(): DiarySentimentDao

    companion object {
        const val KOONDA_DATABASE_NAME = "KoondaDatabase.db"
    }
}
