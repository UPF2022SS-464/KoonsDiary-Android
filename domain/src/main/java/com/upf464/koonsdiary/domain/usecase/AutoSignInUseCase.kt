package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.AutoSignInRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.KakaoService
import javax.inject.Inject

internal class AutoSignInUseCase @Inject constructor(
    val userRepository: UserRepository,
    val kakaoService: KakaoService
) : ResultUseCase<AutoSignInRequest, EmptyResponse> {

    override suspend fun invoke(request: AutoSignInRequest): Result<EmptyResponse> {
        return userRepository.getAutoSignIn().flatMap { type ->
            when (type) {
                SignInType.USERNAME -> autoSignInWithToken()
                SignInType.KAKAO -> autoSignInWithKakao()
                null -> Result.failure(SignInError.NoAutoSignIn)
            }
        }.flatMap {
            Result.success(EmptyResponse)
        }.onFailure { error ->
            if (error !is SignInError.NoAutoSignIn) {
                userRepository.clearAutoSignIn()
            }
        }
    }

    private suspend fun autoSignInWithToken(): Result<Unit> {
        return userRepository.getAutoSignInToken().flatMap { token ->
            userRepository.signInWithToken(token)
        }.flatMap { token ->
            token?.let {
                userRepository.setAutoSignInWithToken(token)
            }
            Result.success(Unit)
        }
    }

    private suspend fun autoSignInWithKakao(): Result<Unit> {
        return kakaoService.getAccessToken().flatMap { token ->
            userRepository.signInWithKakao(token)
        }
    }
}