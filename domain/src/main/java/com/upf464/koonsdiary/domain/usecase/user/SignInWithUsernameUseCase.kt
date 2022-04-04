package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.SignInWithUsernameRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.MessageService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SignInWithUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val hashGenerator: HashGenerator,
    private val messageService: MessageService,
    private val messageRepository: MessageRepository
) : ResultUseCase<SignInWithUsernameRequest, EmptyResponse> {

    override suspend fun invoke(request: SignInWithUsernameRequest): Result<EmptyResponse> {
        return userRepository.fetchSaltOf(request.username).flatMap { salt ->
            userRepository.signInWithUsername(
                request.username,
                hashGenerator.hashPasswordWithSalt(request.password, salt)
            )
        }.flatMap {
            messageService.getToken()
        }.flatMap { token ->
            messageRepository.registerFcmToken(token)
        }.map {
            EmptyResponse
        }
    }
}