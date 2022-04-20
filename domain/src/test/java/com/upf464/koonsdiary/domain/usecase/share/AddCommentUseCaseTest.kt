package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.repository.ShareRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddCommentUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    private lateinit var useCase: AddCommentUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = AddCommentUseCase(shareRepository)
    }

    @Test
    fun invoke_validComment_isSuccess(): Unit = runBlocking {
        coEvery {
            shareRepository.addComment(any())
        } returns Result.success(Unit)

        val result = useCase(AddCommentUseCase.Request(1, "content"))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_invalidDiaryId_throwsInvalidDiaryIdError(): Unit = runBlocking {
        coEvery {
            shareRepository.addComment(any())
        } returns Result.failure(ShareError.InvalidDiaryId)

        val result = useCase(AddCommentUseCase.Request(1, "content"))

        assertTrue(result.isFailure)
        assertEquals(ShareError.InvalidDiaryId, result.exceptionOrNull())
    }

    @Test
    fun invoke_emptyContent_throwsEmptyContentError(): Unit = runBlocking {

        val result = useCase(AddCommentUseCase.Request(1, ""))

        assertTrue(result.isFailure)
        assertEquals(ShareError.EmptyContent, result.exceptionOrNull())
    }
}
