package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.common.SignUpValidator
import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.SignUpWithUsernameRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignUpWithUsernameUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var validator: SignUpValidator
    private lateinit var hashGenerator: HashGenerator
    private lateinit var useCase: SignUpWithUsernameUseCase

    @Before
    fun setup() {
        userRepository = mockkClass(UserRepository::class)
        validator = mockkClass(SignUpValidator::class)
        hashGenerator = mockkClass(HashGenerator::class)
        useCase = SignUpWithUsernameUseCase(
            userRepository = userRepository,
            validator = validator,
            hashGenerator = hashGenerator
        )
    }

    @Test
    fun invoke_validInput_isSuccess(): Unit = runBlocking {
        every {
            validator.isEmailValid(any())
        } returns true

        every {
            validator.isPasswordValid(any())
        } returns true

        every {
            validator.isUsernameValid(any())
        } returns true

        every {
            validator.isNicknameValid(any())
        } returns true

        coEvery {
            userRepository.generateSaltOf("username")
        } returns Result.success("salt")

        every {
            hashGenerator.hashPasswordWithSalt("password", "salt")
        } returns "passwordWithSalt"

        coEvery {
            userRepository.signUpWithUsername(any())
        } returns Result.success("token")

        coEvery {
            userRepository.setAutoSignInWithToken("token")
        } returns Result.success(Unit)

        val result = useCase(SignUpWithUsernameRequest(
            email = "email",
            username = "username",
            password = "password",
            nickname = "nickname"
        ))

        assertTrue(result.isSuccess)
        coVerify {
            userRepository.setAutoSignInWithToken("token")
        }
    }

    @Test
    fun invoke_invalidEmail_isFailure(): Unit = runBlocking {
        every {
            validator.isEmailValid(any())
        } returns false

        val result = useCase(SignUpWithUsernameRequest(
            email = "email",
            username = "username",
            password = "password",
            nickname = "nickname"
        ))

        assertFalse(result.isSuccess)
        assertEquals(SignUpError.InvalidEmail, result.exceptionOrNull())
    }

    @Test
    fun invoke_invalidUsername_isFailure(): Unit = runBlocking {
        every {
            validator.isEmailValid(any())
        } returns true

        every {
            validator.isUsernameValid(any())
        } returns false

        val result = useCase(SignUpWithUsernameRequest(
            email = "email",
            username = "username",
            password = "password",
            nickname = "nickname"
        ))

        assertFalse(result.isSuccess)
        assertEquals(SignUpError.InvalidUsername, result.exceptionOrNull())
    }

    @Test
    fun invoke_invalidPassword_isFailure(): Unit = runBlocking {
        every {
            validator.isEmailValid(any())
        } returns true

        every {
            validator.isUsernameValid(any())
        } returns true

        every {
            validator.isPasswordValid(any())
        } returns false

        val result = useCase(SignUpWithUsernameRequest(
            email = "email",
            username = "username",
            password = "password",
            nickname = "nickname"
        ))

        assertFalse(result.isSuccess)
        assertEquals(SignUpError.InvalidPassword, result.exceptionOrNull())
    }

    @Test
    fun invoke_invalidNickname_isFailure(): Unit = runBlocking {
        every {
            validator.isEmailValid(any())
        } returns true

        every {
            validator.isUsernameValid(any())
        } returns true

        every {
            validator.isPasswordValid(any())
        } returns true

        every {
            validator.isNicknameValid(any())
        } returns false

        val result = useCase(SignUpWithUsernameRequest(
            email = "email",
            username = "username",
            password = "password",
            nickname = "nickname"
        ))

        assertFalse(result.isSuccess)
        assertEquals(SignUpError.InvalidNickname, result.exceptionOrNull())
    }
}