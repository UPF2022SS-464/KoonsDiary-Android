package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.common.DiaryValidator
import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AnalyzeSentimentUseCaseTest {

    @MockK private lateinit var validator: DiaryValidator
    @MockK private lateinit var diaryRepository: DiaryRepository
    private lateinit var useCase: AnalyzeSentimentUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = AnalyzeSentimentUseCase(
            validator = validator,
            diaryRepository = diaryRepository
        )
    }

    @Test
    fun invoke_validDiary_isSuccess(): Unit = runBlocking {
        coEvery {
            diaryRepository.fetchSentimentOf("content")
        } returns Result.success(Sentiment.GOOD)

        every {
            validator.validateContent("content")
        } returns true

        val result = useCase(
            AnalyzeSentimentUseCase.Request("content")
        )

        assertTrue(result.isSuccess)
        assertEquals(Sentiment.GOOD, result.getOrNull()?.sentiment)
    }

    @Test
    fun invoke_emptyDiary_isFailure(): Unit = runBlocking {
        every {
            validator.validateContent("")
        } returns false

        val result = useCase(
            AnalyzeSentimentUseCase.Request("")
        )

        assertFalse(result.isSuccess)
        assertEquals(DiaryError.EmptyContent, result.exceptionOrNull())
    }
}
