package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.SignInWithKakaoRequest
import com.upf464.koonsdiary.domain.request.SignInWithUsernameRequest
import com.upf464.koonsdiary.domain.request.SignUpWithUsernameRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.SignInWithKakaoUseCase
import com.upf464.koonsdiary.domain.usecase.SignInWithUsernameUseCase
import com.upf464.koonsdiary.domain.usecase.SignUpWithUsernameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserUseCaseModule {

    @Binds
    abstract fun bindSignInWithUsernameUseCase(
        useCase: SignInWithUsernameUseCase
    ) : ResultUseCase<SignInWithUsernameRequest, EmptyResponse>

    @Binds
    abstract fun bindSignInWithKakaoUseCase(
        useCase: SignInWithKakaoUseCase
    ) : ResultUseCase<SignInWithKakaoRequest, EmptyResponse>

    @Binds
    abstract fun bindSignUpWithUsernameUseCase(
        useCase: SignUpWithUsernameUseCase
    ) : ResultUseCase<SignUpWithUsernameRequest, EmptyResponse>
}
