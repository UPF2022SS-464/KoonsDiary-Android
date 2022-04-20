package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.service.SecurityService
import javax.inject.Inject

class SetBiometricUseCase @Inject constructor(
    private val securityRepository: SecurityRepository,
    private val securityService: SecurityService
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        val authentication = if (request.isActive) {
            securityService.authenticateWithBiometric()
        } else Result.success(Unit)

        return authentication.flatMap {
            securityRepository.setBiometric(request.isActive)
        }
    }

    data class Request(
        val isActive: Boolean
    )
}
