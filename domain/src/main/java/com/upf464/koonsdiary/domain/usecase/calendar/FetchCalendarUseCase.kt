package com.upf464.koonsdiary.domain.usecase.calendar

import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.calendar.FetchCalendarRequest
import com.upf464.koonsdiary.domain.response.calendar.FetchCalendarResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

class FetchCalendarUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ResultUseCase<FetchCalendarRequest, FetchCalendarResponse> {

    override suspend fun invoke(request: FetchCalendarRequest): Result<FetchCalendarResponse> {
        return diaryRepository.fetchMonthlySentiment(
            year = request.year,
            month = request.month
        ).map { sentimentList ->
            FetchCalendarResponse(sentimentList)
        }
    }
}