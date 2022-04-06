package com.upf464.koonsdiary.domain.usecase.calendar

import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.calendar.FetchCalendarRequest
import com.upf464.koonsdiary.domain.response.calendar.FetchCalendarResponse
import com.upf464.koonsdiary.domain.usecase.FlowResultUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FetchCalendarFlowUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) : FlowResultUseCase<FetchCalendarRequest, FetchCalendarResponse> {

    override fun invoke(request: FetchCalendarRequest): Flow<Result<FetchCalendarResponse>> {
        return diaryRepository.fetchMonthlySentimentFlow(
            year = request.year,
            month = request.month
        ).map { result ->
            result.map { sentimentList ->
                FetchCalendarResponse(sentimentList)
            }
        }
    }
}