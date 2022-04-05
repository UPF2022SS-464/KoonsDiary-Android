package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.security.*
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.security.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SecurityUseCaseModule {

    @Binds
    abstract fun bindSavePINUseCase(
        useCase: SavePINUseCase
    ): ResultUseCase<SavePINRequest, EmptyResponse>

    @Binds
    abstract fun bindDeletePINUseCase(
        useCase: DeletePINUseCase
    ): ResultUseCase<DeletePINRequest, EmptyResponse>

    @Binds
    abstract fun bindAuthenticateWithPINUseCase(
        useCase: AuthenticateWithPINUseCase
    ): ResultUseCase<AuthenticateWithPINRequest, EmptyResponse>

    @Binds
    abstract fun bindAuthenticateWithBiometricUseCase(
        useCase: AuthenticateWithBiometricUseCase
    ): ResultUseCase<AuthenticateWithBiometricRequest, EmptyResponse>

    @Binds
    abstract fun bindSetBiometricUseCase(
        useCase: SetBiometricUseCase
    ): ResultUseCase<SetBiometricRequest, EmptyResponse>
}