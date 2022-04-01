package com.upf464.koonsdiary.firebase.di

import com.upf464.koonsdiary.domain.service.MessageService
import com.upf464.koonsdiary.firebase.service.MessageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ServiceModule {

    @Binds
    abstract fun bindMessageService(
        service: MessageServiceImpl
    ): MessageService
}