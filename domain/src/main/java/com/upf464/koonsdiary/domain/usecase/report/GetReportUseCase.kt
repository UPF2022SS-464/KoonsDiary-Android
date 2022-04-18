package com.upf464.koonsdiary.domain.usecase.report

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.error.ReportError
import com.upf464.koonsdiary.domain.model.DateTerm
import com.upf464.koonsdiary.domain.repository.ReportRepository
import com.upf464.koonsdiary.domain.request.report.GetReportRequest
import com.upf464.koonsdiary.domain.response.report.GetReportResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class GetReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) : ResultUseCase<GetReportRequest, GetReportResponse> {

    override suspend fun invoke(request: GetReportRequest): Result<GetReportResponse> {

        val startDate = request.startDate
        val dateTerm = request.dateTerm

        val endDate = when (dateTerm.type) {
            DateTerm.Type.DAY -> startDate.plusDays(dateTerm.term)
            DateTerm.Type.WEEK -> startDate.plusWeeks(dateTerm.term)
            DateTerm.Type.MONTH -> startDate.plusMonths(dateTerm.term)
        }

        return reportRepository.fetchAllSentiment().flatMap { sentimentMap ->
            val filteredSentiment = sentimentMap.filter { (date, _) ->
                startDate <= date && date < endDate
            }

            val sentimentCountMap = filteredSentiment.values.groupingBy { it }.eachCount()
            val totalDays = sentimentCountMap.values.sum()

            val sentimentPercentageMap = sentimentCountMap.mapValues { it.value * 100 / totalDays }

            val mostSentiment = sentimentCountMap.maxByOrNull { it.value }?.key
                ?: return Result.failure(ReportError.NoSentiment)

            val graphList = (1..dateTerm.term).map { index ->
                val sentimentScoreList = filteredSentiment.filter { (date, _) ->
                    when (dateTerm.type) {
                        DateTerm.Type.DAY ->
                            startDate.plusDays(index - 1) <= date &&
                                    date < startDate.plusDays(index)
                        DateTerm.Type.WEEK ->
                            startDate.plusWeeks(index - 1) <= date &&
                                    date < startDate.plusWeeks(index)
                        DateTerm.Type.MONTH ->
                            startDate.plusMonths(index - 1) <= date &&
                                    date < startDate.plusMonths(index)
                    }
                }.values.map { it.ordinal }

                if (sentimentScoreList.isEmpty()) null
                else sentimentScoreList.sum().toDouble() / sentimentScoreList.size
            }

            reportRepository.fetchKoonsMention(mostSentiment).map { mention ->
                GetReportResponse(graphList, sentimentPercentageMap, mostSentiment, mention)
            }
        }
    }
}