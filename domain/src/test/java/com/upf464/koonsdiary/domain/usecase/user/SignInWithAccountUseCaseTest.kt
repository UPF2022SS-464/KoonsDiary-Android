package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.service.MessageService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SignInWithAccountUseCaseTest {

    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var hashGenerator: HashGenerator
    @MockK private lateinit var messageService: MessageService
    @MockK private lateinit var messageRepository: MessageRepository
    @MockK private lateinit var securityRepository: SecurityRepository
    private lateinit var useCase: SignInWithAccountUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SignInWithAccountUseCase(
            userRepository = userRepository,
            hashGenerator = hashGenerator,
            messageService = messageService,
            messageRepository = messageRepository,
            securityRepository = securityRepository
        )
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
            userRepository.signInWithAccount("username", "passwordWithSalt")
        } returns Result.success("token")

        coEvery {
            userRepository.setAutoSignInWithToken("token")
        } returns Result.success(Unit)

        coEvery {
            messageService.getToken()
        } returns Result.success("token")

        coEvery {
            messageRepository.registerFcmToken("token")
        } returns Result.success(Unit)

        coEvery {
            securityRepository.clearPIN()
        } returns Result.success(Unit)

        val result = useCase(SignInWithAccountUseCase.Request("username", "password"))

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
            userRepository.signInWithAccount("username", "passwordWithSalt")
        } returns Result.failure(SignInError.IncorrectUsernameOrPassword)

        val result = useCase(SignInWithAccountUseCase.Request("username", "password"))

        assertFalse(result.isSuccess)
        assertEquals(SignInError.IncorrectUsernameOrPassword, result.exceptionOrNull())
    }
}
