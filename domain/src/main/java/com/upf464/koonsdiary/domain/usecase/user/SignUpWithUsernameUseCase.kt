package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.SignUpWithUsernameRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.MessageService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SignUpWithUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val securityRepository: SecurityRepository,
    private val hashGenerator: HashGenerator,
    private val messageService: MessageService,
    private val messageRepository: MessageRepository
) : ResultUseCase<SignUpWithUsernameRequest, EmptyResponse> {

    override suspend fun invoke(request: SignUpWithUsernameRequest): Result<EmptyResponse> {
        return userRepository.generateSaltOf(request.username).flatMap { salt ->
            val hashedPassword = hashGenerator.hashPasswordWithSalt(request.password, salt)

            val user = User(
                username = request.username,
                email = request.email,
                nickname = request.nickname,
                image = User.Image(id = request.imageId)
            )

            userRepository.signUpWithUsername(user, hashedPassword)
        }.onSuccess { token ->
            userRepository.setAutoSignInWithToken(token)
            securityRepository.clearPIN()
        }.flatMap {
            messageService.getToken()
        }.flatMap { token ->
            messageRepository.registerFcmToken(token)
        }.map {
            EmptyResponse
        }
    }
}
