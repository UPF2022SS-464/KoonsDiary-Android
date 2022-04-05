package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.error.SecurityError
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.request.security.SetBiometricRequest
import com.upf464.koonsdiary.domain.service.SecurityService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SetBiometricUseCaseTest {

    @MockK private lateinit var securityService: SecurityService
    @MockK private lateinit var securityRepository: SecurityRepository
    private lateinit var useCase: SetBiometricUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SetBiometricUseCase(
            securityRepository = securityRepository,
            securityService = securityService
        )
    }

    @Test
    fun invoke_setTrueWithValidAuthentication_isSuccess(): Unit = runBlocking {
        coEvery {
            securityService.authenticateWithBiometric()
        } returns Result.success(Unit)

        coEvery {
            securityRepository.setBiometric(true)
        } returns Result.success(Unit)

        val result = useCase(SetBiometricRequest(true))

        assertTrue(result.isSuccess)
        coVerify { securityService.authenticateWithBiometric() }
    }

    @Test
    fun invoke_setFalse_isSuccess(): Unit = runBlocking {
        coEvery {
            securityRepository.setBiometric(false)
        } returns Result.success(Unit)

        val result = useCase(SetBiometricRequest(false))

        assertTrue(result.isSuccess)
        coVerify(exactly = 0) { securityService.authenticateWithBiometric() }
    }

    @Test
    fun invoke_setTrueWithInvalidAuthentication_throwSecurityError(): Unit = runBlocking {
        coEvery {
            securityService.authenticateWithBiometric()
        } returns Result.failure(SecurityError.AuthenticateFailed)

        coEvery {
            securityRepository.setBiometric(true)
        } returns Result.failure(SecurityError.AuthenticateFailed)

        val result = useCase(SetBiometricRequest(true))

        assertTrue(result.isFailure)
        assertEquals(SecurityError.AuthenticateFailed, result.exceptionOrNull())
        coVerify { securityService.authenticateWithBiometric() }
    }
}