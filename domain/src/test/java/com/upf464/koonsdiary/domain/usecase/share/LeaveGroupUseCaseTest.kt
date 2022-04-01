package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.repository.ShareRepository
import com.upf464.koonsdiary.domain.request.share.LeaveGroupRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LeaveGroupUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    private lateinit var useCase: LeaveGroupUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = LeaveGroupUseCase(shareRepository)
    }

    @Test
    fun invoke_validInformation_isSuccess(): Unit = runBlocking {
        coEvery {
            shareRepository.leaveGroup(any())
        } returns Result.success(Unit)

        val result = useCase(LeaveGroupRequest(1))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_invalidGroupId_throwsInvalidGroupIdError(): Unit = runBlocking {
        coEvery {
            shareRepository.leaveGroup(any())
        } returns Result.failure(ShareError.InvalidGroupId)

        val result = useCase(LeaveGroupRequest(1))

        assertTrue(result.isFailure)
        assertEquals(ShareError.InvalidGroupId, result.exceptionOrNull())
    }
}