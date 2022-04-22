package com.upf464.koonsdiary.kakao.di

import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object KakaoModule {

    @Provides
    fun provideUserApiClient(): UserApiClient = UserApiClient.instance

    @Provides
    fun provideAuthApiClient(): AuthApiClient = AuthApiClient.instance
}
