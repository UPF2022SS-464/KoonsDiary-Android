package com.upf464.koonsdiary.data.di

import com.upf464.koonsdiary.data.repository.CottonRepositoryImpl
import com.upf464.koonsdiary.data.repository.DiaryRepositoryImpl
import com.upf464.koonsdiary.data.repository.ShareRepositoryImpl
import com.upf464.koonsdiary.data.repository.UserRepositoryImpl
import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
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
}