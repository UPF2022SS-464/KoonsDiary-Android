package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.request.security.AuthenticateWithBiometricRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.SecurityService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class AuthenticateWithBiometricUseCase @Inject constructor(
    private val securityService: SecurityService
) : ResultUseCase<AuthenticateWithBiometricRequest, EmptyResponse> {

    override suspend fun invoke(request: AuthenticateWithBiometricRequest): Result<EmptyResponse> {
        return securityService.authenticateWithBiometric().map {
            EmptyResponse
        }
    }
}