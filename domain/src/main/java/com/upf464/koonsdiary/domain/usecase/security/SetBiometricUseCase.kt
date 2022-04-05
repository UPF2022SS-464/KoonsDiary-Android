package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.request.security.SetBiometricRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.SecurityService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SetBiometricUseCase @Inject constructor(
    private val securityRepository: SecurityRepository,
    private val securityService: SecurityService
) : ResultUseCase<SetBiometricRequest, EmptyResponse> {

    override suspend fun invoke(request: SetBiometricRequest): Result<EmptyResponse> {
        val authentication = if (request.isActive) {
            securityService.authenticateWithBiometric()
        } else Result.success(Unit)

        return authentication.flatMap {
            securityRepository.setBiometric(request.isActive)
        }.map {
            EmptyResponse
        }
    }
}