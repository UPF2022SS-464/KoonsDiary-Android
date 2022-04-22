package com.upf464.koonsdiary.kakao.di

import com.upf464.koonsdiary.data.source.KakaoRemoteDataSource
import com.upf464.koonsdiary.kakao.source.KakaoRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindKakaoRemoteDataSource(
        source: KakaoRemoteDataSourceImpl
    ): KakaoRemoteDataSource
}
