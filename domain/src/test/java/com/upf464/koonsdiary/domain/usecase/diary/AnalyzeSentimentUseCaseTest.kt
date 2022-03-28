package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.common.DiaryValidator
import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.diary.AnalyzeSentimentRequest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AnalyzeSentimentUseCaseTest {

    private lateinit var validator: DiaryValidator
    private lateinit var diaryRepository: DiaryRepository
    private lateinit var useCase: AnalyzeSentimentUseCase

    @Before
    fun setup() {
        validator = mockkClass(DiaryValidator::class)
        diaryRepository = mockkClass(DiaryRepository::class)
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
            AnalyzeSentimentRequest("content")
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
            AnalyzeSentimentRequest("")
        )

        assertFalse(result.isSuccess)
        assertEquals(DiaryError.EmptyContent, result.exceptionOrNull())
    }
}