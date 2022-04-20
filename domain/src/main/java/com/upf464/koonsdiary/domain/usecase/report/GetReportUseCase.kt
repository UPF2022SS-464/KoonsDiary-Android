package com.upf464.koonsdiary.domain.usecase.report

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.error.ReportError
import com.upf464.koonsdiary.domain.model.DateTerm
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.ReportRepository
import java.time.LocalDate
import javax.inject.Inject

class GetReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {

    suspend operator fun invoke(request: Request): Result<Response> {

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

            val mostCount = sentimentCountMap.maxByOrNull { it.value }?.value
                ?: return Result.failure(ReportError.NoSentiment)

            val mostSentimentSet = sentimentCountMap.filter { (_, count) ->
                count == mostCount
            }.keys

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

            reportRepository.fetchKoonsMention(mostSentimentSet).map { mention ->
                Response(graphList, sentimentPercentageMap, mostSentimentSet, mention)
            }
        }
    }

    data class Request(
        val dateTerm: DateTerm,
        val startDate: LocalDate,
    )

    data class Response(
        val graphList: List<Double?> = emptyList(),
        val sentimentPercentageMap: Map<Sentiment, Int> = emptyMap(),
        val mostSentimentSet: Set<Sentiment>,
        val koonsMention: String
    )
}
