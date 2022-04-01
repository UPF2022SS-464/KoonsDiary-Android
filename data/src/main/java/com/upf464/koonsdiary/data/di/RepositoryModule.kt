package com.upf464.koonsdiary.data.di

import com.upf464.koonsdiary.data.repository.*
import com.upf464.koonsdiary.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindDiaryRepository(
        repository: DiaryRepositoryImpl
    ): DiaryRepository

    @Binds
    abstract fun bindCottonRepository(
        repository: CottonRepositoryImpl
    ): CottonRepository

    @Binds
    abstract fun bindShareRepository(
        repository: ShareRepositoryImpl
    ): ShareRepository

    @Binds
    abstract fun bindMessageRepository(
        repository: MessageRepositoryImpl
    ): MessageRepository
}