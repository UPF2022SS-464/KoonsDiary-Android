package com.upf464.koonsdiary.domain.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.MessageDigest

@Module
@InstallIn(SingletonComponent::class)
internal object DomainModule {

    fun provideMessageDigest() : MessageDigest = MessageDigest.getInstance("SHA-256")
}