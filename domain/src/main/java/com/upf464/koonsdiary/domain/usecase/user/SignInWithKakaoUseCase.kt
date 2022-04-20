package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.common.extension.handleWith
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.service.KakaoService
import com.upf464.koonsdiary.domain.service.MessageService
import javax.inject.Inject

class SignInWithKakaoUseCase @Inject constructor(
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository,
    private val securityRepository: SecurityRepository,
    private val messageService: MessageService,
    private val messageRepository: MessageRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return trySignInWithToken().handleWith { error ->
            if (error is SignInError.AccessTokenExpired) {
                kakaoService.signInWithKakao()
                trySignInWithToken()
            } else Result.failure(error)
        }.flatMap {
            messageService.getToken()
        }.flatMap { token ->
            messageRepository.registerFcmToken(token)
        }.onSuccess {
            userRepository.setAutoSignInWithKakao()
            securityRepository.clearPIN()
        }
    }

    private suspend fun trySignInWithToken(): Result<Unit> =
        kakaoService.getAccessToken().flatMap { token ->
            userRepository.signInWithKakao(token)
        }
}
