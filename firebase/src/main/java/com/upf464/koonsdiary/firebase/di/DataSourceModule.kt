package com.upf464.koonsdiary.firebase.di

import com.upf464.koonsdiary.data.source.FirebaseRemoteDataSource
import com.upf464.koonsdiary.firebase.source.FirebaseRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindFirebaseRemoteDataSource(
        source: FirebaseRemoteDataSourceImpl
    ): FirebaseRemoteDataSource
}
