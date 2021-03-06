package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

class AutoSignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val messageRepository: MessageRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return userRepository.getAutoSignIn().flatMap { type ->
            when (type) {
                SignInType.USERNAME -> autoSignInWithToken()
                SignInType.KAKAO -> autoSignInWithKakao()
                null -> Result.failure(SignInError.NoAutoSignIn)
            }
        }.flatMap {
            messageRepository.getToken()
        }.flatMap { token ->
            messageRepository.registerFcmToken(token)
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
        return userRepository.getKakaoAccessToken().flatMap { token ->
            userRepository.signInWithKakao(token)
        }
    }
}
