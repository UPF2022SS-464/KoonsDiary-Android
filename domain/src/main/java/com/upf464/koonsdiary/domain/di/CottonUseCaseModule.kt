package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.cotton.GetRandomQuestionRequest
import com.upf464.koonsdiary.domain.response.cotton.GetRandomQuestionResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.cotton.GetRandomQuestionUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal abstract class CottonUseCaseModule {

    @Binds
    abstract fun bindGetRandomQuestionUseCase(
        useCase: GetRandomQuestionUseCase
    ): ResultUseCase<GetRandomQuestionRequest, GetRandomQuestionResponse>

}
