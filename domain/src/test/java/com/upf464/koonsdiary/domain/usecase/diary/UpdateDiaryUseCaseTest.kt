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
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class UpdateDiaryUseCaseTest {

    @MockK private lateinit var validator: DiaryValidator
    @MockK private lateinit var diaryRepository: DiaryRepository
    private lateinit var useCase: UpdateDiaryUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = UpdateDiaryUseCase(
            validator = validator,
            diaryRepository = diaryRepository
        )
    }

    @Test
    fun invoke_validDiary_isSuccess(): Unit = runBlocking {
        coEvery {
            diaryRepository.updateDiary(any())
        } returns Result.success(1)

        every {
            validator.validateContent("content")
        } returns true

        val result = useCase(
            UpdateDiaryUseCase.Request(
                diaryId = 1,
                date = LocalDate.of(2022, 3, 25),
                content = "content",
                sentiment = Sentiment.GOOD,
                imageList = emptyList()
            )
        )

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.diaryId)
    }

    @Test
    fun invoke_emptyDiary_isFailure(): Unit = runBlocking {
        every {
            validator.validateContent("")
        } returns false

        val result = useCase(
            UpdateDiaryUseCase.Request(
                diaryId = 1,
                date = LocalDate.of(2022, 3, 25),
                content = "",
                sentiment = Sentiment.GOOD,
                imageList = emptyList()
            )
        )

        Assert.assertFalse(result.isSuccess)
        assertEquals(DiaryError.EmptyContent, result.exceptionOrNull())
    }

    @Test
    fun invoke_invalidDiaryId_isFailure(): Unit = runBlocking {
        coEvery {
            diaryRepository.updateDiary(any())
        } returns Result.failure(DiaryError.InvalidDiaryId)

        every {
            validator.validateContent("content")
        } returns true

        val result = useCase(
            UpdateDiaryUseCase.Request(
                diaryId = 1,
                date = LocalDate.of(2022, 3, 25),
                content = "content",
                sentiment = Sentiment.GOOD,
                imageList = emptyList()
            )
        )

        Assert.assertFalse(result.isSuccess)
        assertEquals(DiaryError.InvalidDiaryId, result.exceptionOrNull())
    }
}
