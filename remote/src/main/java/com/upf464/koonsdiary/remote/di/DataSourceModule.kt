package com.upf464.koonsdiary.remote.di

import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import com.upf464.koonsdiary.remote.source.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindUserRemoteDataSource(
        source: UserRemoteDataSourceImpl
    ): UserRemoteDataSource
}