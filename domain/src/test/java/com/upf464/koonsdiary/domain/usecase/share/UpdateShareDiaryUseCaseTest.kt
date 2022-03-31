package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.UpdateShareDiaryRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdateShareDiaryUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    private lateinit var useCase: UpdateShareDiaryUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = UpdateShareDiaryUseCase(shareRepository)
    }

    @Test
    fun invoke_validInformation_isSuccess(): Unit = runBlocking {
        coEvery {
            shareRepository.updateDiary(any())
        } returns Result.success(1)

        val result = useCase(
            UpdateShareDiaryRequest(
                diaryId = 1,
                content = "content",
                imageList = emptyList()
            )
        )

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.diaryId)
    }

    @Test
    fun invoke_emptyContent_isFailure(): Unit = runBlocking {

        val result = useCase(
            UpdateShareDiaryRequest(
                diaryId = 1,
                content = "",
                imageList = emptyList()
            )
        )

        assertTrue(result.isFailure)
        assertEquals(ShareError.EmptyContent, result.exceptionOrNull())
    }

    @Test
    fun invoke_invalidDiaryId_isFailure(): Unit = runBlocking {
        coEvery {
            shareRepository.updateDiary(any())
        } returns Result.failure(ShareError.InvalidDiaryId)

        val result = useCase(
            UpdateShareDiaryRequest(
                diaryId = 1,
                content = "content",
                imageList = emptyList()
            )
        )

        assertTrue(result.isFailure)
        assertEquals(ShareError.InvalidDiaryId, result.exceptionOrNull())
    }
}