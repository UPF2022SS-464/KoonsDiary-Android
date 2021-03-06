package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.UserValidator
import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        if (request.nickname == null && request.imageId == null) {
            return Result.failure(SignUpError.NotEnoughInformation)
        }

        if (request.nickname != null && !userValidator.isNicknameValid(request.nickname)) {
            return Result.failure(SignUpError.InvalidNickname)
        }

        return userRepository.updateUser(request.nickname, request.imageId)
    }

    data class Request(
        val nickname: String? = null,
        val imageId: Int? = null
    )
}
