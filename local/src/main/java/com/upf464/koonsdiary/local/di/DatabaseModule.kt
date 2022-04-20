package com.upf464.koonsdiary.local.di

import android.content.Context
import androidx.room.Room
import com.upf464.koonsdiary.local.database.KoondaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): KoondaDatabase = Room.databaseBuilder(
        context,
        KoondaDatabase::class.java,
        KoondaDatabase.KOONDA_DATABASE_NAME
    ).build()
}
