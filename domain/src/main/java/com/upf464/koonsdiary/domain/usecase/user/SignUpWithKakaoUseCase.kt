package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.common.handleWith
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.SignUpWithKakaoRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.KakaoService
import com.upf464.koonsdiary.domain.service.MessageService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SignUpWithKakaoUseCase @Inject constructor(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository,
    private val messageService: MessageService,
    private val messageRepository: MessageRepository
) : ResultUseCase<SignUpWithKakaoRequest, EmptyResponse> {

    override suspend fun invoke(request: SignUpWithKakaoRequest): Result<EmptyResponse> {
        val user = User(
            username = request.username,
            nickname = request.nickname
        )

        return trySignUpWithToken(user).handleWith { error ->
            if (error is SignInError.AccessTokenExpired) {
                kakaoService.signInWithKakao()
                trySignUpWithToken(user)
            } else Result.failure(error)
        }.flatMap {
            messageService.getToken()
        }.flatMap { token ->
            messageRepository.registerFcmToken(token)
        }.map {
            EmptyResponse
        }.onSuccess {
            userRepository.setAutoSignInWithKakao()
        }
    }

    private suspend fun trySignUpWithToken(user: User): Result<Unit> =
        kakaoService.getAccessToken().flatMap { token ->
            userRepository.signUpWithKakao(user, token)
        }
}