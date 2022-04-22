package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

class SignUpWithAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val securityRepository: SecurityRepository,
    private val hashGenerator: HashGenerator,
    private val messageRepository: MessageRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        return userRepository.generateSaltOf(request.username).flatMap { salt ->
            val hashedPassword = hashGenerator.hashPasswordWithSalt(request.password, salt)

            val user = User(
                username = request.username,
                email = request.email,
                nickname = request.nickname,
                image = User.Image(id = request.imageId)
            )

            userRepository.signUpWithAccount(user, hashedPassword)
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
        val email: String,
        val username: String,
        val password: String,
        val nickname: String,
        val imageId: Int
    )
}
