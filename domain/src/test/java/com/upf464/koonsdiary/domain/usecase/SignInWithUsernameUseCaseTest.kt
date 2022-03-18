package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.SignInWithUsernameRequest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
        every {
            hashGenerator.hashPasswordWithSalt("username", "password")
        } returns "password"

        coEvery {
            userRepository.signInWithUsername("username", "password")
        } returns Result.success(Unit)

        val result = useCase(SignInWithUsernameRequest("username", "password"))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_incorrectUsernameAndPassword_isFailure(): Unit = runBlocking {
        every {
            hashGenerator.hashPasswordWithSalt("username", "password")
        } returns "password"

        coEvery {
            userRepository.signInWithUsername("username", "password")
        } returns Result.failure(Exception())

        val result = useCase(SignInWithUsernameRequest("username", "password"))

        assertFalse(result.isSuccess)
    }
}