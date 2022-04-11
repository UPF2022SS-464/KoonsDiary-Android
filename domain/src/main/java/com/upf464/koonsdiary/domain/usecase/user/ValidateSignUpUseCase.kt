package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.UserValidator
import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.ValidateSignUpRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class ValidateSignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator
) : ResultUseCase<ValidateSignUpRequest, EmptyResponse> {

    override suspend fun invoke(request: ValidateSignUpRequest): Result<EmptyResponse> {
        return when (request.type) {
            ValidateSignUpRequest.Type.EMAIL -> {
                if (userValidator.isEmailValid(request.content)) {
                    userRepository.isEmailDuplicated(request.content).map {
                        EmptyResponse
                    }
                } else Result.failure(SignUpError.InvalidEmail)
            }
            ValidateSignUpRequest.Type.USERNAME -> {
                if (userValidator.isUsernameValid(request.content)) {
                    userRepository.isUsernameDuplicated(request.content).map {
                        EmptyResponse
                    }
                } else Result.failure(SignUpError.InvalidUsername)
            }
            ValidateSignUpRequest.Type.PASSWORD ->
                if (userValidator.isPasswordValid(request.content)) Result.success(EmptyResponse)
                else Result.failure(SignUpError.InvalidPassword)
            ValidateSignUpRequest.Type.NICKNAME ->
                if (userValidator.isNicknameValid(request.content)) Result.success(EmptyResponse)
                else Result.failure(SignUpError.InvalidNickname)
        }
    }
}
