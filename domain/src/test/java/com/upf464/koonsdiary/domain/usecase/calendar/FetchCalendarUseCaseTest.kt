package com.upf464.koonsdiary.domain.usecase.calendar

import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.calendar.FetchCalendarRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchCalendarUseCaseTest {

    @MockK private lateinit var diaryRepository: DiaryRepository
    private lateinit var useCase: FetchCalendarUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchCalendarUseCase(diaryRepository = diaryRepository)
    }

    @Test
    fun invoke_validYearAndMonth_isSuccess(): Unit = runBlocking {
        coEvery {
            diaryRepository.fetchMonthlySentiment(
                year = 2022,
                month = 3
            )
        } returns Result.success(listOf(
            Sentiment.GOOD,
            Sentiment.NORMAL,
            Sentiment.SAD
        ))

        val result = useCase.invoke(FetchCalendarRequest(year = 2022, month = 3))

        assertTrue(result.isSuccess)
        assertEquals(
            listOf(Sentiment.GOOD, Sentiment.NORMAL, Sentiment.SAD),
            result.getOrNull()?.sentimentList
        )
    }
}