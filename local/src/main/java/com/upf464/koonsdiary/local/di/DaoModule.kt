package com.upf464.koonsdiary.local.di

import com.upf464.koonsdiary.local.dao.DiarySentimentDao
import com.upf464.koonsdiary.local.database.KoondaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideDiarySentimentDao(
        database: KoondaDatabase
    ): DiarySentimentDao = database.getDiaryDao()
}