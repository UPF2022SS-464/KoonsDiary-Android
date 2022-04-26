package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.model.DiaryPreview
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class FetchDiaryPreviewUseCaseTest {

    @MockK private lateinit var diaryRepository: DiaryRepository
    private lateinit var useCase: FetchDiaryPreviewUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = FetchDiaryPreviewUseCase(diaryRepository)
    }

    @Test
    fun invoke_validDiaryId_isSuccess(): Unit = runBlocking {
        coEvery {
            diaryRepository.fetchDiaryPreview(any())
        } returns Result.success(DiaryPreview(1, LocalDate.of(2022, 4, 26), "", ""))

        val result = useCase(FetchDiaryPreviewUseCase.Request(LocalDate.of(2022, 4, 26)))

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.preview?.id)
    }

    @Test
    fun invoke_invalidDiaryId_isFailure(): Unit = runBlocking {
        coEvery {
            diaryRepository.fetchDiaryPreview(any())
        } returns Result.failure(DiaryError.NoPreview)

        val result = useCase(FetchDiaryPreviewUseCase.Request(LocalDate.of(2022, 4, 26)))

        assertTrue(result.isFailure)
        assertEquals(DiaryError.NoPreview, result.exceptionOrNull())
    }
}
