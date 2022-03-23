package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.AnalyzeSentimentRequest
import com.upf464.koonsdiary.domain.response.AnalyzeSentimentResponse
import com.upf464.koonsdiary.domain.usecase.AnalyzeSentimentUseCase
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DiaryUseCaseModule {

    @Binds
    abstract fun bindAnalyzeSentimentUseCase(
        useCase: AnalyzeSentimentUseCase
    ) : ResultUseCase<AnalyzeSentimentRequest, AnalyzeSentimentResponse>
}
