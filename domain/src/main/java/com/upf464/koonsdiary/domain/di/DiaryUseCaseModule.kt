package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.diary.*
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.response.diary.*
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.diary.*
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

    @Binds
    abstract fun bindAddDiaryUseCase(
        useCase: AddDiaryUseCase
    ) : ResultUseCase<AddDiaryRequest, AddDiaryResponse>

    @Binds
    abstract fun bindUpdateDiaryUseCase(
        useCase: UpdateDiaryUseCase
    ) : ResultUseCase<UpdateDiaryRequest, UpdateDiaryResponse>

    @Binds
    abstract fun bindDeleteDiaryUseCase(
        useCase: DeleteDiaryUseCase
    ) : ResultUseCase<DeleteDiaryRequest, EmptyResponse>

    @Binds
    abstract fun bindFetchDiaryUseCase(
        useCase: FetchDiaryUseCase
    ) : ResultUseCase<FetchDiaryRequest, FetchDiaryResponse>

    @Binds
    abstract fun bindFetchDiaryPreviewUseCase(
        useCase: FetchDiaryPreviewUseCase
    ) : ResultUseCase<FetchDiaryPreviewRequest, FetchDiaryPreviewResponse>
}
