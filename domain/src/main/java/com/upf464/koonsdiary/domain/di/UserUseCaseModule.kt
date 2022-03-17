package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.LoginWithKakaoRequest
import com.upf464.koonsdiary.domain.request.LoginWithUsernameRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.LoginWithKakaoUseCase
import com.upf464.koonsdiary.domain.usecase.LoginWithUsernameUseCase
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserUseCaseModule {

    @Binds
    abstract fun bindLoginWithUsernameUseCase(
        useCase: LoginWithUsernameUseCase
    ) : ResultUseCase<LoginWithUsernameRequest, EmptyResponse>

    @Binds
    abstract fun bindLoginWithKakaoUseCase(
        useCase: LoginWithKakaoUseCase
    ) : ResultUseCase<LoginWithKakaoRequest, EmptyResponse>
}