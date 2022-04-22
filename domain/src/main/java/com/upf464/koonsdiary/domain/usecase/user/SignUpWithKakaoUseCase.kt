package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.common.extension.handleWith
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

class SignUpWithKakaoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val securityRepository: SecurityRepository,
    private val messageRepository: MessageRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        val user = User(
            username = request.username,
            nickname = request.nickname,
            image = User.Image(id = request.imageId)
        )

        return trySignUpWithToken(user).handleWith { error ->
            if (error is SignInError.AccessTokenExpired) {
                userRepository.signInKakaoAccount()
                trySignUpWithToken(user)
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

    private suspend fun trySignUpWithToken(user: User): Result<Unit> =
        userRepository.getKakaoAccessToken().flatMap { token ->
            userRepository.signUpWithKakao(user, token)
        }

    data class Request(
        val username: String,
        val nickname: String,
        val imageId: Int
    )
}
