package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

class SignInWithAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val securityRepository: SecurityRepository,
    private val hashGenerator: HashGenerator,
    private val messageRepository: MessageRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        return userRepository.fetchSaltOf(request.username).flatMap { salt ->
            userRepository.signInWithAccount(
                request.username,
                hashGenerator.hashPasswordWithSalt(request.password, salt)
            )
        }.onSuccess { token ->
            userRepository.setAutoSignInWithToken(token)
            securityRepository.clearPIN()
        }.flatMap {
            messageRepository.getToken()
        }.flatMap { token ->
            messageRepository.registerFcmToken(token)
        }
    }

    data class Request(
        val username: String,
        val password: String
    )
}
