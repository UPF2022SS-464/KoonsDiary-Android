package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.model.Message
import com.upf464.koonsdiary.domain.request.message.GetMessageFlowRequest
import com.upf464.koonsdiary.domain.request.message.RegisterFcmTokenRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.FlowUseCase
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.message.GetMessageFlowUseCase
import com.upf464.koonsdiary.domain.usecase.message.RegisterFcmTokenUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MessageUseCaseModule {

    @Binds
    abstract fun bindGetMessageFlowUseCase(
        useCase: GetMessageFlowUseCase
    ): FlowUseCase<GetMessageFlowRequest, Message>

    @Binds
    abstract fun bindRegisterFcmTokenUseCase(
        useCase: RegisterFcmTokenUseCase
    ): ResultUseCase<RegisterFcmTokenRequest, EmptyResponse>
}