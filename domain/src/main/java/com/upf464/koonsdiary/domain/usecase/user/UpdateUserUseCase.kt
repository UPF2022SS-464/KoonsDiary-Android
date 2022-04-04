package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.UserValidator
import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.user.UpdateUserRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator
) : ResultUseCase<UpdateUserRequest, EmptyResponse> {

    override suspend fun invoke(request: UpdateUserRequest): Result<EmptyResponse> {
        if (!userValidator.isNicknameValid(request.nickname)) {
            return Result.failure(SignUpError.InvalidNickname)
        }

        return userRepository.updateUser(request.nickname).map {
            EmptyResponse
        }
    }
}