package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.common.extension.handleWith
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

class SignInWithKakaoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val securityRepository: SecurityRepository,
    private val messageRepository: MessageRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return trySignInWithToken().handleWith { error ->
            if (error is SignInError.AccessTokenExpired) {
                userRepository.signInKakaoAccount()
                trySignInWithToken()
            } else Result.failure(error)
        }.flatMap {
            messageRepository.getToken()
        }.flatMap { token ->
            messageRepository.registerFcmToken(token)
        }.onSuccess {
            userRepository.setAutoSignInWithKakao()
            securityRepository.clearPIN()
        }
    }

    private suspend fun trySignInWithToken(): Result<Unit> =
        userRepository.getKakaoAccessToken().flatMap { token ->
            userRepository.signInWithKakao(token)
        }
}
