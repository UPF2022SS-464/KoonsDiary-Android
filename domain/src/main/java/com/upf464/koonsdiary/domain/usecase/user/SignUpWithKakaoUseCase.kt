package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.common.handleWith
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.SignUpWithKakaoRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.KakaoService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SignUpWithKakaoUseCase @Inject constructor(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository
) : ResultUseCase<SignUpWithKakaoRequest, EmptyResponse> {

    override suspend fun invoke(request: SignUpWithKakaoRequest): Result<EmptyResponse> {
        return trySignUpWithToken(request.nickname).handleWith { error ->
            if (error is SignInError.AccessTokenExpired) {
                kakaoService.signInWithKakao()
                trySignUpWithToken(request.nickname)
            } else Result.failure(error)
        }.map {
            EmptyResponse
        }.onSuccess {
            userRepository.setAutoSignInWithKakao()
        }
    }

    private suspend fun trySignUpWithToken(nickname: String): Result<Unit> =
        kakaoService.getAccessToken().flatMap { token ->
            userRepository.signUpWithKakao(token, nickname)
        }
}