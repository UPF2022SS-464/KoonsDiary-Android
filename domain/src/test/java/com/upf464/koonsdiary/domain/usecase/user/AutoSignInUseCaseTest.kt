package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AutoSignInUseCaseTest {

    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var messageRepository: MessageRepository
    private lateinit var useCase: AutoSignInUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = AutoSignInUseCase(
            userRepository = userRepository,
            messageRepository = messageRepository
        )
    }

    @Test
    fun invoke_kakao_isSuccess(): Unit = runBlocking {
        coEvery {
            userRepository.getAutoSignIn()
        } returns Result.success(SignInType.KAKAO)

        coEvery {
            userRepository.getKakaoAccessToken()
        } returns Result.success("token")

        coEvery {
            userRepository.signInWithKakao("token")
        } returns Result.success(Unit)

        coEvery {
            messageRepository.getToken()
        } returns Result.success("token")

        coEvery {
            messageRepository.registerFcmToken("token")
        } returns Result.success(Unit)

        val result = useCase()

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_username_isSuccess(): Unit = runBlocking {
        coEvery {
            userRepository.getAutoSignIn()
        } returns Result.success(SignInType.USERNAME)

        coEvery {
            userRepository.getAutoSignInToken()
        } returns Result.success("token")

        coEvery {
            userRepository.signInWithToken("token")
        } returns Result.success("new token")

        coEvery {
            userRepository.setAutoSignInWithToken("new token")
        } returns Result.success(Unit)

        coEvery {
            messageRepository.getToken()
        } returns Result.success("token")

        coEvery {
            messageRepository.registerFcmToken("token")
        } returns Result.success(Unit)

        val result = useCase()

        assertTrue(result.isSuccess)
        coVerify { userRepository.setAutoSignInWithToken("new token") }
    }

    @Test
    fun invoke_usernameNoToken_isFailure(): Unit = runBlocking {
        coEvery {
            userRepository.getAutoSignIn()
        } returns Result.success(SignInType.USERNAME)

        coEvery {
            userRepository.getAutoSignInToken()
        } returns Result.failure(SignInError.AccessTokenExpired)

        coEvery {
            userRepository.clearAutoSignIn()
        } returns Result.success(Unit)

        val result = useCase()

        assertTrue(result.isFailure)
        coVerify { userRepository.clearAutoSignIn() }
    }

    @Test
    fun invoke_noAutoSignIn_isFailure(): Unit = runBlocking {
        coEvery {
            userRepository.getAutoSignIn()
        } returns Result.failure(SignInError.NoAutoSignIn)

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals(SignInError.NoAutoSignIn, result.exceptionOrNull())
    }
}
