package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.service.SecurityService
import javax.inject.Inject

class AuthenticateWithBiometricUseCase @Inject constructor(
    private val securityService: SecurityService
) {

    suspend operator fun invoke(): Result<Unit> {
        return securityService.authenticateWithBiometric()
    }
}
