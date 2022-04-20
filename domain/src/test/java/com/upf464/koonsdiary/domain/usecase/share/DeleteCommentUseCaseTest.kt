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

class DeleteCommentUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    private lateinit var useCase: DeleteCommentUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = DeleteCommentUseCase(shareRepository)
    }

    @Test
    fun invoke_validDiaryId_isSuccess(): Unit = runBlocking {
        coEvery {
            shareRepository.deleteComment(1)
        } returns Result.success(Unit)

        val result = useCase(DeleteCommentUseCase.Request(1))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_invalidDiaryId_throwsInvalidDiaryIdError(): Unit = runBlocking {
        coEvery {
            shareRepository.deleteComment(1)
        } returns Result.failure(ShareError.InvalidDiaryId)

        val result = useCase(DeleteCommentUseCase.Request(1))

        assertTrue(result.isFailure)
        assertEquals(ShareError.InvalidDiaryId, result.exceptionOrNull())
    }
}
