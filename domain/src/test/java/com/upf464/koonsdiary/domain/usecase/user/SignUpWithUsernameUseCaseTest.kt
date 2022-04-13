package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.SignUpWithUsernameRequest
import com.upf464.koonsdiary.domain.service.MessageService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SignUpWithUsernameUseCaseTest {

    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var hashGenerator: HashGenerator
    @MockK private lateinit var messageService: MessageService
    @MockK private lateinit var messageRepository: MessageRepository
    @MockK private lateinit var securityRepository: SecurityRepository
    private lateinit var useCase: SignUpWithUsernameUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SignUpWithUsernameUseCase(
            userRepository = userRepository,
            hashGenerator = hashGenerator,
            messageService = messageService,
            messageRepository = messageRepository,
            securityRepository = securityRepository
        )
    }

    @Test
    fun invoke_validInput_isSuccess(): Unit = runBlocking {
        coEvery {
            userRepository.generateSaltOf("username")
        } returns Result.success("salt")

        every {
            hashGenerator.hashPasswordWithSalt("password", "salt")
        } returns "passwordWithSalt"

        coEvery {
            userRepository.signUpWithUsername(any(), any())
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

        val result = useCase(
            SignUpWithUsernameRequest(
                email = "email",
                username = "username",
                password = "password",
                nickname = "nickname",
                imageId = 1
            )
        )

        assertTrue(result.isSuccess)
        coVerify {
            userRepository.setAutoSignInWithToken("token")
        }
    }
}
