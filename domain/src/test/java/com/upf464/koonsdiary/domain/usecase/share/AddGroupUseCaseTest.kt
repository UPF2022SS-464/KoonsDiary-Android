package com.upf464.koonsdiary.domain.usecase.share

import com.upf464.koonsdiary.domain.common.GroupValidator
import com.upf464.koonsdiary.domain.error.ShareError
import com.upf464.koonsdiary.domain.repository.ShareRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddGroupUseCaseTest {

    @MockK private lateinit var shareRepository: ShareRepository
    @MockK private lateinit var groupValidator: GroupValidator
    private lateinit var useCase: AddGroupUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = AddGroupUseCase(shareRepository, groupValidator)
    }

    @Test
    fun invoke_validGroupInformation_returnsGroupId(): Unit = runBlocking {
        every {
            groupValidator.isGroupNameValid(any())
        } returns true

        coEvery {
            shareRepository.addGroup(any(), any())
        } returns Result.success(1)

        val result = useCase(AddGroupUseCase.Request("name", null, emptyList()))

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.groupId)
    }

    @Test
    fun invoke_invalidGroupName_throwsInvalidGroupNameError(): Unit = runBlocking {
        every {
            groupValidator.isGroupNameValid(any())
        } returns false

        val result = useCase(AddGroupUseCase.Request("invalid name", null, emptyList()))

        assertTrue(result.isFailure)
        assertEquals(ShareError.InvalidGroupName, result.exceptionOrNull())
    }
}
