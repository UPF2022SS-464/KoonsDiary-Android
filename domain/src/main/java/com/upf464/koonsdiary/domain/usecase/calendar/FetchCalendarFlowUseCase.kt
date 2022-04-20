package com.upf464.koonsdiary.domain.usecase.calendar

import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchCalendarFlowUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {

    operator fun invoke(request: Request): Flow<Result<Response>> {
        return diaryRepository.fetchMonthlySentimentFlow(
            year = request.year,
            month = request.month
        ).map { result ->
            result.map { sentimentList ->
                Response(sentimentList)
            }
        }
    }

    data class Request(
        val year: Int,
        val month: Int
    )

    data class Response(
        val sentimentList: List<Sentiment?>
    )
}
