package com.upf464.koonsdiary.domain.usecase.diary

import com.upf464.koonsdiary.domain.error.DiaryError
import com.upf464.koonsdiary.domain.repository.DiaryRepository
import com.upf464.koonsdiary.domain.request.DeleteDiaryRequest
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteDiaryUseCaseTest {

    private lateinit var diaryRepository: DiaryRepository
    private lateinit var useCase: DeleteDiaryUseCase

    @Before
    fun setup() {
        diaryRepository = mockkClass(DiaryRepository::class)
        useCase = DeleteDiaryUseCase(
            diaryRepository = diaryRepository
        )
    }

    @Test
    fun invoke_validDiaryId_isSuccess(): Unit = runBlocking {
        coEvery {
            diaryRepository.deleteDiary(1)
        } returns Result.success(Unit)

        val result = useCase(
            DeleteDiaryRequest(1)
        )

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_invalidDiaryId_isFailure(): Unit = runBlocking {
        coEvery {
            diaryRepository.deleteDiary(1)
        } returns Result.failure(DiaryError.IncorrectDiaryId)

        val result = useCase(
            DeleteDiaryRequest(1)
        )

        assertTrue(result.isFailure)
        assertEquals(DiaryError.IncorrectDiaryId, result.exceptionOrNull())
    }
}