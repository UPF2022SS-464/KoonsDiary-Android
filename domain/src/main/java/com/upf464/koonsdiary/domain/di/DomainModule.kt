package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.common.DiaryValidator
import com.upf464.koonsdiary.domain.common.GroupValidator
import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.common.UserValidator
import com.upf464.koonsdiary.domain.common.impl.DiaryValidatorImpl
import com.upf464.koonsdiary.domain.common.impl.GroupValidatorImpl
import com.upf464.koonsdiary.domain.common.impl.HashGeneratorImpl
import com.upf464.koonsdiary.domain.common.impl.UserValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.MessageDigest

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DomainModule {

    @Binds
    abstract fun bindDiaryValidator(
        validator: DiaryValidatorImpl
    ): DiaryValidator

    @Binds
    abstract fun bindGroupValidator(
        validator: GroupValidatorImpl
    ): GroupValidator

    @Binds
    abstract fun bindUserValidator(
        validator: UserValidatorImpl
    ): UserValidator

    @Binds
    abstract fun bindHashGenerator(
        generator: HashGeneratorImpl
    ): HashGenerator

    companion object {

        @Provides
        fun provideMessageDigest() : MessageDigest = MessageDigest.getInstance("SHA-256")
    }
}