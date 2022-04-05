package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.request.security.DeletePINRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeletePINUseCaseTest {

    @MockK private lateinit var securityRepository: SecurityRepository
    private lateinit var deletePINUseCase: DeletePINUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deletePINUseCase = DeletePINUseCase(securityRepository)
    }

    @Test
    fun invoke_nothing_isSuccess(): Unit = runBlocking {
        coEvery {
            securityRepository.clearPIN()
        } returns Result.success(Unit)

        val result = deletePINUseCase(DeletePINRequest)

        assertTrue(result.isSuccess)
    }
}