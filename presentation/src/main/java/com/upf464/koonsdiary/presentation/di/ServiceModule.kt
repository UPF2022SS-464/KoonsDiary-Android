package com.upf464.koonsdiary.presentation.di

import com.upf464.koonsdiary.domain.service.SecurityService
import com.upf464.koonsdiary.presentation.service.SecurityServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ServiceModule {

    @Binds
    abstract fun bindSecurityService(
        service: SecurityServiceImpl
    ): SecurityService
}
