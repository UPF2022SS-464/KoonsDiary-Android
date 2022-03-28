package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.calendar.FetchCalendarRequest
import com.upf464.koonsdiary.domain.response.calendar.FetchCalendarResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.calendar.FetchCalendarUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CalendarUseCaseModule {

    @Binds
    abstract fun bindFetchCalendarUseCase(
        useCase: FetchCalendarUseCase
    ): ResultUseCase<FetchCalendarRequest, FetchCalendarResponse>
}
