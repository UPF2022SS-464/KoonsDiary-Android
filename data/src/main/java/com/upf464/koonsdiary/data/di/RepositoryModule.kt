package com.upf464.koonsdiary.data.di

import com.upf464.koonsdiary.data.repository.CottonRepositoryImpl
import com.upf464.koonsdiary.data.repository.DiaryRepositoryImpl
import com.upf464.koonsdiary.data.repository.MessageRepositoryImpl
import com.upf464.koonsdiary.data.repository.NotificationRepositoryImpl
import com.upf464.koonsdiary.data.repository.ReportRepositoryImpl
import com.upf464.koonsdiary.data.repository.SecurityRepositoryImpl
import com.upf464.koonsdiary.data.repository.ShareRepositoryImpl
import com.upf464.koonsdiary.data.repository.UserRepositoryImpl
import com.upf464.koonsdiary.domain.repository.CottonRepository
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.NotificationRepository
import com.upf464.koonsdiary.domain.repository.ReportRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindDiaryRepository(
        repository: DiaryRepositoryImpl
    ): DiaryRepository

    @Binds
    @Singleton
    abstract fun bindCottonRepository(
        repository: CottonRepositoryImpl
    ): CottonRepository

    @Binds
    @Singleton
    abstract fun bindShareRepository(
        repository: ShareRepositoryImpl
    ): ShareRepository

    @Binds
    @Singleton
    abstract fun bindMessageRepository(
        repository: MessageRepositoryImpl
    ): MessageRepository

    @Binds
    @Singleton
    abstract fun bindSecurityRepository(
        repository: SecurityRepositoryImpl
    ): SecurityRepository

    @Binds
    @Singleton
    abstract fun bindReportRepository(
        repository: ReportRepositoryImpl
    ): ReportRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        repository: NotificationRepositoryImpl
    ): NotificationRepository
}
