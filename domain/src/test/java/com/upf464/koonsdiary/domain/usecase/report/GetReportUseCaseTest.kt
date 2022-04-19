package com.upf464.koonsdiary.domain.usecase.report

import com.upf464.koonsdiary.domain.error.ReportError
import com.upf464.koonsdiary.domain.model.DateTerm
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.ReportRepository
import com.upf464.koonsdiary.domain.request.report.GetReportRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetReportUseCaseTest {

    @MockK
    private lateinit var reportRepository: ReportRepository
    private lateinit var useCase: GetReportUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetReportUseCase(
            reportRepository = reportRepository
        )
        coEvery {
            reportRepository.fetchAllSentiment()
        } returns Result.success(
            mapOf(
                LocalDate.of(2022, 4, 4) to Sentiment.GOOD,
                LocalDate.of(2022, 4, 6) to Sentiment.GOOD,
                LocalDate.of(2022, 4, 7) to Sentiment.GOOD,
                LocalDate.of(2022, 4, 11) to Sentiment.NORMAL,
                LocalDate.of(2022, 4, 18) to Sentiment.NORMAL,
                LocalDate.of(2022, 6, 11) to Sentiment.NORMAL,
                LocalDate.of(2022, 5, 30) to Sentiment.NORMAL,
                LocalDate.of(2022, 10, 11) to Sentiment.NORMAL,
            )
        )
    }

    @Test
    fun invoke_dayReport_isSuccess(): Unit = runBlocking {
        coEvery {
            reportRepository.fetchKoonsMention(mostSentimentSet = setOf(Sentiment.NORMAL))
        } returns Result.success("")

        val result = useCase(GetReportRequest(DateTerm.DAY_7, LocalDate.of(2022, 4, 11)))

        assertTrue(result.isSuccess)
        assertEquals(setOf(Sentiment.NORMAL), result.getOrNull()?.mostSentimentSet)
    }

    @Test
    fun invoke_weekReport_isSuccess(): Unit = runBlocking {
        coEvery {
            reportRepository.fetchKoonsMention(mostSentimentSet = setOf(Sentiment.NORMAL))
        } returns Result.success("")

        val result = useCase(GetReportRequest(DateTerm.WEEK_8, LocalDate.of(2022, 4, 11)))

        assertTrue(result.isSuccess)
        assertEquals(setOf(Sentiment.NORMAL), result.getOrNull()?.mostSentimentSet)
    }

    @Test
    fun invoke_monthReport_isSuccess(): Unit = runBlocking {
        coEvery {
            reportRepository.fetchKoonsMention(mostSentimentSet = setOf(Sentiment.NORMAL))
        } returns Result.success("")

        val result = useCase(GetReportRequest(DateTerm.MONTH_6, LocalDate.of(2022, 4, 11)))

        assertTrue(result.isSuccess)
        assertEquals(setOf(Sentiment.NORMAL), result.getOrNull()?.mostSentimentSet)
    }

    @Test
    fun invoke_emptySentiment_throwsNoSentimentError(): Unit = runBlocking {

        coEvery {
            reportRepository.fetchKoonsMention(any())
        } returns Result.failure(ReportError.NoSentiment)

        val result = useCase(GetReportRequest(DateTerm.MONTH_6, LocalDate.of(2023, 4, 11)))

        assertTrue(result.isFailure)
        assertEquals(ReportError.NoSentiment, result.exceptionOrNull())
    }
}