package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.SignInWithUsernameRequest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignInWithUsernameUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var hashGenerator: HashGenerator
    private lateinit var useCase: SignInWithUsernameUseCase

    @Before
    fun setup() {
        userRepository = mockkClass(UserRepository::class)
        hashGenerator = mockkClass(HashGenerator::class)
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
        } returns Result.success(Unit)

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