package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.LoginWithUsernameRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import javax.inject.Inject

internal class LoginWithUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository
) : ResultUseCase<LoginWithUsernameRequest, EmptyResponse> {

    override suspend fun invoke(request: LoginWithUsernameRequest): Result<EmptyResponse> {
        return userRepository.loginWithUsername(request.username, request.password).flatMap {
            Result.success(EmptyResponse)
        }
    }
}