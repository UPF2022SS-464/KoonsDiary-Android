package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.error.SecurityError
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthenticateWithPINUseCaseTest {

    @MockK private lateinit var securityRepository: SecurityRepository
    @MockK private lateinit var hashGenerator: HashGenerator
    private lateinit var useCase: AuthenticateWithPINUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = AuthenticateWithPINUseCase(securityRepository, hashGenerator)
    }

    @Test
    fun invoke_validPIN_isSuccess(): Unit = runBlocking {
        coEvery {
            securityRepository.fetchDisposableSalt()
        } returns Result.success("salt")

        coEvery {
            hashGenerator.hashPasswordWithSalt("0000", "salt")
        } returns "password with salt"

        coEvery {
            securityRepository.fetchPIN()
        } returns Result.success("password with salt")

        val result = useCase(AuthenticateWithPINUseCase.Request("0000"))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_invalidPIN_throwInvalidPINError(): Unit = runBlocking {
        coEvery {
            securityRepository.fetchDisposableSalt()
        } returns Result.success("salt")

        coEvery {
            hashGenerator.hashPasswordWithSalt("0001", "salt")
        } returns "wrong password with salt"

        coEvery {
            securityRepository.fetchPIN()
        } returns Result.success("password with salt")

        val result = useCase(AuthenticateWithPINUseCase.Request("0001"))

        assertTrue(result.isFailure)
        assertEquals(SecurityError.InvalidPIN, result.exceptionOrNull())
    }
}
