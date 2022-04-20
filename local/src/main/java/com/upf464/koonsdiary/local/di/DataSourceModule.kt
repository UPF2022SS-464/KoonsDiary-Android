package com.upf464.koonsdiary.local.di

import com.upf464.koonsdiary.data.source.DiaryLocalDataSource
import com.upf464.koonsdiary.data.source.SecurityLocalDataSource
import com.upf464.koonsdiary.data.source.UserLocalDataSource
import com.upf464.koonsdiary.local.source.DiaryLocalDataSourceImpl
import com.upf464.koonsdiary.local.source.SecurityLocalDataSourceImpl
import com.upf464.koonsdiary.local.source.UserLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindUserLocalDataSource(
        source: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    abstract fun bindSecurityLocalDataSource(
        source: SecurityLocalDataSourceImpl
    ): SecurityLocalDataSource

    @Binds
    abstract fun bindDiaryLocalDataSource(
        source: DiaryLocalDataSourceImpl
    ): DiaryLocalDataSource
}
