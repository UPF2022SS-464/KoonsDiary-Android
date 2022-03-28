package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.model.Diary
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.FetchDiaryRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchDiaryUseCaseTest {

    @MockK private lateinit var diaryRepository: DiaryRepository
    private lateinit var useCase: FetchDiaryUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchDiaryUseCase(
            diaryRepository = diaryRepository
        )
    }

    @Test
    fun invoke_validDiaryId_isSuccess(): Unit = runBlocking {
        val fetchedDiary = Diary()

        coEvery {
            diaryRepository.fetchDiary(1)
        } returns Result.success(fetchedDiary)

        val result = useCase(FetchDiaryRequest(1))

        assertTrue(result.isSuccess)
        assertEquals(fetchedDiary, result.getOrNull()?.diary)
    }

    @Test
    fun invoke_invalidDiaryId_isFailure(): Unit = runBlocking {
        coEvery {
            diaryRepository.fetchDiary(1)
        } returns Result.failure(DiaryError.InvalidDiaryId)

        val result = useCase(FetchDiaryRequest(1))

        assertTrue(result.isFailure)
        assertEquals(DiaryError.InvalidDiaryId, result.exceptionOrNull())
    }
}