package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.repository.ShareRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchShareDiaryListUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    private lateinit var useCase: FetchShareDiaryListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchShareDiaryListUseCase(shareRepository)
    }

    @Test
    fun invoke_validGroupId_returnsEmptyList(): Unit = runBlocking {
        coEvery {
            shareRepository.fetchDiaryList(1)
        } returns Result.success(emptyList())

        val result = useCase(FetchShareDiaryListUseCase.Request(1))

        assertTrue(result.isSuccess)
        assertEquals(emptyList<ShareDiary>(), result.getOrNull()?.diaryList)
    }

    @Test
    fun invoke_invalidGroupId_throwsInvalidGroupIdError(): Unit = runBlocking {
        coEvery {
            shareRepository.fetchDiaryList(1)
        } returns Result.failure(ShareError.InvalidGroupId)

        val result = useCase(FetchShareDiaryListUseCase.Request(1))

        assertTrue(result.isFailure)
        assertEquals(ShareError.InvalidGroupId, result.exceptionOrNull())
    }
}
