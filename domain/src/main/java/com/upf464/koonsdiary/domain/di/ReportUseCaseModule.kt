package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.report.GetReportRequest
import com.upf464.koonsdiary.domain.response.report.GetReportResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.report.GetReportUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ReportUseCaseModule {

    @Binds
    abstract fun bindGetReportUseCase(
        useCase: GetReportUseCase
    ): ResultUseCase<GetReportRequest, GetReportResponse>
}