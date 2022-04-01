package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.AutoSignInRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.KakaoService
import com.upf464.koonsdiary.domain.service.MessageService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AutoSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val kakaoService: KakaoService,
    private val messageService: MessageService,
    private val messageRepository: MessageRepository
) : ResultUseCase<AutoSignInRequest, EmptyResponse> {

    override suspend fun invoke(request: AutoSignInRequest): Result<EmptyResponse> {
        return userRepository.getAutoSignIn().flatMap { type ->
            when (type) {
                SignInType.USERNAME -> autoSignInWithToken()
                SignInType.KAKAO -> autoSignInWithKakao()
                null -> Result.failure(SignInError.NoAutoSignIn)
            }
        }.flatMap {
            messageService.getToken()
        }.flatMap { token ->
            messageRepository.registerFcmToken(token)
        }.map {
            EmptyResponse
        }.onFailure { error ->
            if (error !is SignInError.NoAutoSignIn) {
                userRepository.clearAutoSignIn()
            }
        }
    }

    private suspend fun autoSignInWithToken(): Result<Unit> {
        return userRepository.getAutoSignInToken().flatMap { token ->
            userRepository.signInWithToken(token)
        }.onSuccess { token ->
            token?.let {
                userRepository.setAutoSignInWithToken(token)
            }
        }.map { }
    }

    private suspend fun autoSignInWithKakao(): Result<Unit> {
        return kakaoService.getAccessToken().flatMap { token ->
            userRepository.signInWithKakao(token)
        }
    }
}