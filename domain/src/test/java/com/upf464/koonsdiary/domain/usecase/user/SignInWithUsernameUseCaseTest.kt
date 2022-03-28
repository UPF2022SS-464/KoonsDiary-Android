package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.SignInWithUsernameRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignInWithUsernameUseCaseTest {

    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var hashGenerator: HashGenerator
    private lateinit var useCase: SignInWithUsernameUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SignInWithUsernameUseCase(userRepository, hashGenerator)
    }

    @Test
    fun invoke_correctUsernameAndPassword_isSuccess(): Unit = runBlocking {
        coEvery {
            userRepository.fetchSaltOf("username")
        } returns Result.success("salt")

        every {
            hashGenerator.hashPasswordWithSalt("password", "salt")
        } returns "passwordWithSalt"

        coEvery {
            userRepository.signInWithUsername("username", "passwordWithSalt")
        } returns Result.success("token")

        val result = useCase(SignInWithUsernameRequest("username", "password"))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_incorrectUsernameAndPassword_isFailure(): Unit = runBlocking {
        coEvery {
            userRepository.fetchSaltOf("username")
        } returns Result.success("salt")

        every {
            hashGenerator.hashPasswordWithSalt("password", "salt")
        } returns "passwordWithSalt"

        coEvery {
            userRepository.signInWithUsername("username", "passwordWithSalt")
        } returns Result.failure(SignInError.IncorrectUsernameOrPassword)

        val result = useCase(SignInWithUsernameRequest("username", "password"))

        assertFalse(result.isSuccess)
        assertEquals(SignInError.IncorrectUsernameOrPassword, result.exceptionOrNull())
    }
}