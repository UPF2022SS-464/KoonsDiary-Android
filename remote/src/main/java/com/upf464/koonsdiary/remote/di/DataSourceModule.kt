package com.upf464.koonsdiary.remote.di

import com.upf464.koonsdiary.data.source.*
import com.upf464.koonsdiary.remote.devsource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindUserRemoteDataSource(
        source: UserRemoteDevDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    abstract fun bindDiaryRemoteDataSource(
        source: DiaryRemoteDevDataSourceImpl
    ): DiaryRemoteDataSource

    @Binds
    abstract fun bindCottonRemoteDevDataSource(
        source: CottonRemoteDevDataSourceImpl
    ): CottonRemoteDataSource

    @Binds
    abstract fun bindShareRemoteDataSource(
        source: ShareRemoteDevDataSourceImpl
    ): ShareRemoteDataSource

    @Binds
    abstract fun bindMessageRemoteDataSource(
        source: MessageRemoteDevDataSourceImpl
    ): MessageRemoteDataSource

    @Binds
    abstract fun bindReportRemoteDataSource(
        source: ReportRemoteDevDataSourceImpl
    ): ReportRemoteDataSource
}