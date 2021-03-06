package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SignInWithKakaoUseCaseTest {

    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var messageRepository: MessageRepository
    @MockK private lateinit var securityRepository: SecurityRepository
    private lateinit var useCase: SignInWithKakaoUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SignInWithKakaoUseCase(
            userRepository = userRepository,
            messageRepository = messageRepository,
            securityRepository = securityRepository
        )
    }

    @Test
    fun invoke_kakaoSignIn_isSuccess(): Unit = runBlocking {
        coEvery {
            userRepository.getKakaoAccessToken()
        } returns Result.success("token")

        coEvery {
            userRepository.signInWithKakao("token")
        } returns Result.success(Unit)

        coEvery {
            userRepository.setAutoSignInWithKakao()
        } returns Result.success(Unit)

        coEvery {
            messageRepository.getToken()
        } returns Result.success("token")

        coEvery {
            messageRepository.registerFcmToken("token")
        } returns Result.success(Unit)

        coEvery {
            securityRepository.clearPIN()
        } returns Result.success(Unit)

        val result = useCase()

        assertTrue(result.isSuccess)

        coVerify {
            userRepository.setAutoSignInWithKakao()
        }
    }

    fun invoke_kakaoCancel_isFailure(): Unit = runBlocking {
        coEvery {
            userRepository.getKakaoAccessToken()
        } returns Result.success("token")

        coEvery {
            userRepository.signInWithKakao("token")
        } returns Result.failure(SignInError.KakaoSignInCancel())

        val result = useCase()

        assertFalse(result.isSuccess)
    }
}
