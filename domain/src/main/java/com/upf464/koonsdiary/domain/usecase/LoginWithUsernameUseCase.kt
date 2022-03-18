package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.repository.UserRepository
import com.upf464.koonsdiary.domain.request.LoginWithUsernameRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import javax.inject.Inject

internal class LoginWithUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val hashGenerator: HashGenerator
) : ResultUseCase<LoginWithUsernameRequest, EmptyResponse> {

    override suspend fun invoke(request: LoginWithUsernameRequest): Result<EmptyResponse> {
        return userRepository.fetchSaltOf(request.username).flatMap { salt ->
            userRepository.loginWithUsername(
                request.username,
                hashGenerator.hashPasswordWithSalt(request.password, salt)
            )
        }.flatMap {
            Result.success(EmptyResponse)
        }
    }
}