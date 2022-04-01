package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.common.SignUpValidator
import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.SignUpWithUsernameRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.MessageService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SignUpWithUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validator: SignUpValidator,
    private val hashGenerator: HashGenerator,
    private val messageService: MessageService,
    private val messageRepository: MessageRepository
) : ResultUseCase<SignUpWithUsernameRequest, EmptyResponse> {

    override suspend fun invoke(request: SignUpWithUsernameRequest): Result<EmptyResponse> {
        when {
            !validator.isEmailValid(request.email) ->
                return Result.failure(SignUpError.InvalidEmail)
            !validator.isUsernameValid(request.username) ->
                return Result.failure(SignUpError.InvalidUsername)
            !validator.isPasswordValid(request.password) ->
                return Result.failure(SignUpError.InvalidPassword)
            !validator.isNicknameValid(request.nickname) ->
                return Result.failure(SignUpError.InvalidNickname)
        }

        return userRepository.generateSaltOf(request.username).flatMap { salt ->
            val hashedPassword = hashGenerator.hashPasswordWithSalt(request.password, salt)

            val user = User(
                username = request.username,
                email = request.email,
                nickname = request.nickname
            )

            userRepository.signUpWithUsername(user, hashedPassword)
        }.onSuccess { token ->
            userRepository.setAutoSignInWithToken(token)
        }.flatMap {
            messageService.getToken()
        }.flatMap { token ->
            messageRepository.registerFcmToken(token)
        }.map {
            EmptyResponse
        }
    }
}