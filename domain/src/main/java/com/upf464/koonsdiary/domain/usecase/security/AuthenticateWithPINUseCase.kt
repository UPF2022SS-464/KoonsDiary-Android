package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.error.SecurityError
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.request.security.AuthenticateWithPINRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AuthenticateWithPINUseCase @Inject constructor(
    private val securityRepository: SecurityRepository,
    private val hashGenerator: HashGenerator
) : ResultUseCase<AuthenticateWithPINRequest, EmptyResponse> {

    override suspend fun invoke(request: AuthenticateWithPINRequest): Result<EmptyResponse> {
        return securityRepository.getDisposableSalt().map { salt ->
            hashGenerator.hashPasswordWithSalt(request.pin, salt)
        }.flatMap { hashedPIN ->
            securityRepository.getPIN().flatMap { storedPIN ->
                if (hashedPIN == storedPIN) Result.success(EmptyResponse)
                else Result.failure(SecurityError.InvalidPIN)
            }
        }
    }
}