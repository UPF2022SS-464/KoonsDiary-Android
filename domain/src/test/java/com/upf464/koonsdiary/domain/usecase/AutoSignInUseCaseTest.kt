package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.AutoSignInRequest
import com.upf464.koonsdiary.domain.service.KakaoService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AutoSignInUseCaseTest {
    private lateinit var kakaoService: KakaoService
    private lateinit var userRepository: UserRepository
    private lateinit var useCase: AutoSignInUseCase

    @Before
    fun setup() {
        kakaoService = mockkClass(KakaoService::class)
        userRepository = mockkClass(UserRepository::class)
        useCase = AutoSignInUseCase(
            userRepository = userRepository,
            kakaoService = kakaoService
        )
    }

    @Test
    fun invoke_kakao_isSuccess(): Unit = runBlocking {
        coEvery {
            userRepository.getAutoSignIn()
        } returns Result.success(SignInType.KAKAO)

        coEvery {
            kakaoService.getAccessToken()
        } returns Result.success("token")

        coEvery {
            userRepository.signInWithKakao("token")
        } returns Result.success(Unit)

        val result = useCase(AutoSignInRequest)

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

        val result = useCase(AutoSignInRequest)

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

        val result = useCase(AutoSignInRequest)

        assertTrue(result.isFailure)
        coVerify { userRepository.clearAutoSignIn() }
    }

    @Test
    fun invoke_noAutoSignIn_isFailure(): Unit = runBlocking {
        coEvery {
            userRepository.getAutoSignIn()
        } returns Result.failure(SignInError.NoAutoSignIn)

        val result = useCase(AutoSignInRequest)

        assertTrue(result.isFailure)
        assertEquals(SignInError.NoAutoSignIn, result.exceptionOrNull())
    }
}