package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.model.Comment
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.FetchCommentListRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchCommentListUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    private lateinit var useCase: FetchCommentListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = FetchCommentListUseCase(shareRepository)
    }

    @Test
    fun invoke_validDiaryId_returnsEmptyList(): Unit = runBlocking {
        coEvery {
            shareRepository.fetchCommentList(1)
        } returns Result.success(emptyList())

        val result = useCase(FetchCommentListRequest(1))

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Comment>(), result.getOrNull()?.commentList)
    }

    @Test
    fun invoke_invalidDiaryId_throwsInvalidDiaryIdError(): Unit = runBlocking {
        coEvery {
            shareRepository.fetchCommentList(1)
        } returns Result.failure(ShareError.InvalidDiaryId)

        val result = useCase(FetchCommentListRequest(1))

        assertTrue(result.isFailure)
        assertEquals(ShareError.InvalidDiaryId, result.exceptionOrNull())
    }
}