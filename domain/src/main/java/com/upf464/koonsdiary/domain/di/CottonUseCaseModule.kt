package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.cotton.AddQuestionAnswerRequest
import com.upf464.koonsdiary.domain.request.cotton.FetchRandomAnswerListRequest
import com.upf464.koonsdiary.domain.request.cotton.FetchRandomQuestionRequest
import com.upf464.koonsdiary.domain.request.cotton.FetchReactionListRequest
import com.upf464.koonsdiary.domain.response.cotton.AddQuestionAnswerResponse
import com.upf464.koonsdiary.domain.response.cotton.FetchRandomAnswerListResponse
import com.upf464.koonsdiary.domain.response.cotton.FetchRandomQuestionResponse
import com.upf464.koonsdiary.domain.response.cotton.FetchReactionListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.cotton.AddQuestionAnswerUseCase
import com.upf464.koonsdiary.domain.usecase.cotton.FetchRandomAnswerListUseCase
import com.upf464.koonsdiary.domain.usecase.cotton.FetchRandomQuestionUseCase
import com.upf464.koonsdiary.domain.usecase.cotton.FetchReactionListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal abstract class CottonUseCaseModule {

    @Binds
    abstract fun bindFetchRandomQuestionUseCase(
        useCase: FetchRandomQuestionUseCase
    ): ResultUseCase<FetchRandomQuestionRequest, FetchRandomQuestionResponse>

    @Binds
    abstract fun bindAddQuestionAnswerUseCase(
        useCase: AddQuestionAnswerUseCase
    ): ResultUseCase<AddQuestionAnswerRequest, AddQuestionAnswerResponse>

    @Binds
    abstract fun bindFetchRandomAnswerListUseCase(
        useCase: FetchRandomAnswerListUseCase
    ): ResultUseCase<FetchRandomAnswerListRequest, FetchRandomAnswerListResponse>

    @Binds
    abstract fun bindFetchReactionListUseCase(
        useCase: FetchReactionListUseCase
    ): ResultUseCase<FetchReactionListRequest, FetchReactionListResponse>
}
