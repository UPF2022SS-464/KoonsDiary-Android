package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.request.security.DeletePINRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class DeletePINUseCase @Inject constructor(
    private val securityRepository: SecurityRepository
) : ResultUseCase<DeletePINRequest, EmptyResponse> {

    override suspend fun invoke(request: DeletePINRequest): Result<EmptyResponse> {
        return securityRepository.clearPIN().map {
            EmptyResponse
        }
    }
}