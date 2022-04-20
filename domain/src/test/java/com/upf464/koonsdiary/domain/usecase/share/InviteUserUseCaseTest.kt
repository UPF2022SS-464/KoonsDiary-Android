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

class InviteUserUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    private lateinit var useCase: InviteUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = InviteUserUseCase(shareRepository)
    }

    @Test
    fun invoke_validInformation_isSuccess(): Unit = runBlocking {
        coEvery {
            shareRepository.inviteUser(any(), any())
        } returns Result.success(Unit)

        val result = useCase(InviteUserUseCase.Request(1, listOf(1, 2, 3)))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_emptyUserList_throwsEmptyContentError(): Unit = runBlocking {

        val result = useCase(InviteUserUseCase.Request(1, listOf()))

        assertTrue(result.isFailure)
        assertEquals(ShareError.EmptyContent, result.exceptionOrNull())
    }

    @Test
    fun invoke_invalidGroupId_throwsInvalidGroupIdError(): Unit = runBlocking {
        coEvery {
            shareRepository.inviteUser(any(), any())
        } returns Result.failure(ShareError.InvalidGroupId)

        val result = useCase(InviteUserUseCase.Request(1, listOf(1, 2, 3)))

        assertTrue(result.isFailure)
        assertEquals(ShareError.InvalidGroupId, result.exceptionOrNull())
    }

    @Test
    fun invoke_invalidUserId_throwsInvalidUserIdError(): Unit = runBlocking {
        coEvery {
            shareRepository.inviteUser(any(), any())
        } returns Result.failure(ShareError.InvalidUserId)

        val result = useCase(InviteUserUseCase.Request(1, listOf(1, 2, 3)))

        assertTrue(result.isFailure)
        assertEquals(ShareError.InvalidUserId, result.exceptionOrNull())
    }
}
