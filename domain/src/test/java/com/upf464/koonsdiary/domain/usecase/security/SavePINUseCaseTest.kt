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

class SavePINUseCaseTest {

    @MockK private lateinit var securityRepository: SecurityRepository
    @MockK private lateinit var hashGenerator: HashGenerator
    private lateinit var useCase: SavePINUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SavePINUseCase(securityRepository, hashGenerator)
    }

    @Test
    fun invoke_validPIN_isSuccess(): Unit = runBlocking {
        coEvery {
            securityRepository.fetchDisposableSalt()
        } returns Result.success("salt")

        coEvery {
            hashGenerator.hashPasswordWithSalt("0000", "salt")
        } returns "pin with salt"

        coEvery {
            securityRepository.setPIN(any())
        } returns Result.success(Unit)

        val result = useCase(SavePINUseCase.Request("0000"))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_invalidPIN_throwInvalidPINError(): Unit = runBlocking {

        val result = useCase(SavePINUseCase.Request("000"))

        assertTrue(result.isFailure)
        assertEquals(SecurityError.InvalidPIN, result.exceptionOrNull())
    }
}
