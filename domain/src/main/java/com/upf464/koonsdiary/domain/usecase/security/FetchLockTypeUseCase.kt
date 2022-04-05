package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.request.security.FetchLockTypeRequest
import com.upf464.koonsdiary.domain.response.security.FetchLockTypeResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class FetchLockTypeUseCase @Inject constructor(
    private val securityRepository: SecurityRepository
) : ResultUseCase<FetchLockTypeRequest, FetchLockTypeResponse> {

    override suspend fun invoke(request: FetchLockTypeRequest): Result<FetchLockTypeResponse> {
        return securityRepository.fetchLockType().map { type ->
            FetchLockTypeResponse(type)
        }
    }
}