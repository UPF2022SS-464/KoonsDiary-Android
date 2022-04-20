package com.upf464.koonsdiary.domain.usecase.user

import com.upf464.koonsdiary.domain.common.UserValidator
import com.upf464.koonsdiary.domain.error.SignUpError
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

class ValidateSignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        return when (request.type) {
            Request.Type.EMAIL -> {
                if (userValidator.isEmailValid(request.content)) {
                    userRepository.isEmailDuplicated(request.content)
                } else Result.failure(SignUpError.InvalidEmail)
            }
            Request.Type.USERNAME -> {
                if (userValidator.isUsernameValid(request.content)) {
                    userRepository.isUsernameDuplicated(request.content)
                } else Result.failure(SignUpError.InvalidUsername)
            }
            Request.Type.PASSWORD ->
                if (userValidator.isPasswordValid(request.content)) Result.success(Unit)
                else Result.failure(SignUpError.InvalidPassword)
            Request.Type.NICKNAME ->
                if (userValidator.isNicknameValid(request.content)) Result.success(Unit)
                else Result.failure(SignUpError.InvalidNickname)
        }
    }

    data class Request(
        val type: Type,
        val content: String
    ) {

        enum class Type {
            EMAIL,
            USERNAME,
            PASSWORD,
            NICKNAME
        }
    }
}
