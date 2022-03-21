package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.SignUpWithKakaoRequest
import com.upf464.koonsdiary.domain.service.KakaoService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SignUpWithKakaoUseCaseTest {

    private lateinit var kakaoService: KakaoService
    private lateinit var userRepository: UserRepository
    private lateinit var useCase: SignUpWithKakaoUseCase

    @Before
    fun setup() {
        kakaoService = mockkClass(KakaoService::class)
        userRepository = mockkClass(UserRepository::class)
        useCase = SignUpWithKakaoUseCase(kakaoService, userRepository)
    }

    @Test
    fun invoke_hasToken_isSuccess(): Unit = runBlocking {
        coEvery {
            kakaoService.getAccessToken()
        } returns Result.success("token")

        coEvery {
            userRepository.signUpWithKakao("token", "nickname")
        } returns Result.success(Unit)

        coEvery {
            userRepository.setAutoSignInWithKakao()
        } returns Result.success(Unit)

        val result = useCase(SignUpWithKakaoRequest("nickname"))

        assertTrue(result.isSuccess)

        coVerify {
            userRepository.setAutoSignInWithKakao()
        }
    }

    @Test
    fun invoke_noToken_isSuccess(): Unit = runBlocking {
        coEvery {
            kakaoService.getAccessToken()
        } returns Result.failure(SignInError.AccessTokenExpired)

        coEvery {
            kakaoService.signInWithKakao()
        } answers {
            coEvery {
                kakaoService.getAccessToken()
            } returns Result.success("token")

            Result.success(Unit)
        }

        coEvery {
            userRepository.signUpWithKakao("token", "nickname")
        } returns Result.success(Unit)

        coEvery {
            userRepository.setAutoSignInWithKakao()
        } returns Result.success(Unit)

        val result = useCase(SignUpWithKakaoRequest("nickname"))

        assertTrue(result.isSuccess)
    }
}