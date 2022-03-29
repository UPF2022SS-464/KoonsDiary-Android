package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.share.SearchUserRequest
import com.upf464.koonsdiary.domain.response.share.SearchUserResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.share.SearchUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ShareUseCaseModule {

    @Binds
    abstract fun bindSearchUserUseCase(
        useCase: SearchUserUseCase
    ): ResultUseCase<SearchUserRequest, SearchUserResponse>
}