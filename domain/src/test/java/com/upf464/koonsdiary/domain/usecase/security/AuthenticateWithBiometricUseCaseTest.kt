package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.error.SecurityError
import com.upf464.koonsdiary.domain.request.security.AuthenticateWithBiometricRequest
import com.upf464.koonsdiary.domain.service.SecurityService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthenticateWithBiometricUseCaseTest {

    @MockK private lateinit var securityService: SecurityService
    private lateinit var useCase: AuthenticateWithBiometricUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = AuthenticateWithBiometricUseCase(securityService)
    }

    @Test
    fun invoke_validAuthentication_isSuccess(): Unit = runBlocking {
        coEvery {
            securityService.authenticateWithBiometric()
        } returns Result.success(Unit)

        val result = useCase(AuthenticateWithBiometricRequest)

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_userCancelled_throwCancelledError(): Unit = runBlocking {
        coEvery {
            securityService.authenticateWithBiometric()
        } returns Result.failure(SecurityError.Cancelled)

        val result = useCase(AuthenticateWithBiometricRequest)

        assertTrue(result.isFailure)
        assertEquals(SecurityError.Cancelled, result.exceptionOrNull())
    }
}