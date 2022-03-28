package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.common.handleWith
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.SignInWithKakaoRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.KakaoService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SignInWithKakaoUseCase @Inject constructor(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository
) : ResultUseCase<SignInWithKakaoRequest, EmptyResponse> {

    override suspend fun invoke(request: SignInWithKakaoRequest): Result<EmptyResponse> {
        return trySignInWithToken().handleWith { error ->
            if (error is SignInError.AccessTokenExpired) {
                kakaoService.signInWithKakao()
                trySignInWithToken()
            } else Result.failure(error)
        }.map {
            EmptyResponse
        }.onSuccess {
            userRepository.setAutoSignInWithKakao()
        }
    }

    private suspend fun trySignInWithToken(): Result<Unit> =
        kakaoService.getAccessToken().flatMap { token ->
            userRepository.signInWithKakao(token)
        }
}