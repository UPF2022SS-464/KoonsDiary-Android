package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.AddDiaryRequest
import com.upf464.koonsdiary.domain.request.AnalyzeSentimentRequest
import com.upf464.koonsdiary.domain.request.DeleteDiaryRequest
import com.upf464.koonsdiary.domain.request.UpdateDiaryRequest
import com.upf464.koonsdiary.domain.response.AddDiaryResponse
import com.upf464.koonsdiary.domain.response.AnalyzeSentimentResponse
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.response.UpdateDiaryResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.diary.AddDiaryUseCase
import com.upf464.koonsdiary.domain.usecase.diary.AnalyzeSentimentUseCase
import com.upf464.koonsdiary.domain.usecase.diary.DeleteDiaryUseCase
import com.upf464.koonsdiary.domain.usecase.diary.UpdateDiaryUseCase
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
}
