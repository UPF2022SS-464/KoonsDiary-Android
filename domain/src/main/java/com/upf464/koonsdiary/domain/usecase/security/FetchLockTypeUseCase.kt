package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.error.LockType
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import javax.inject.Inject

class FetchLockTypeUseCase @Inject constructor(
    private val securityRepository: SecurityRepository
) {

    suspend operator fun invoke(): Result<Response> {
        return securityRepository.fetchLockType().map { type ->
            Response(type)
        }
    }

    data class Response(
        val type: LockType
    )
}
