package com.upf464.koonsdiary.kakao.di

import com.upf464.koonsdiary.domain.service.KakaoService
import com.upf464.koonsdiary.kakao.service.KakaoServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ServiceModule {

    @Binds
    abstract fun bindKakaoService(
        service: KakaoServiceImpl
    ): KakaoService
}