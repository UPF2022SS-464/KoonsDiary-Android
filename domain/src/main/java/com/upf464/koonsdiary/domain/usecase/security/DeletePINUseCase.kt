package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.domain.repository.SecurityRepository
import javax.inject.Inject

class DeletePINUseCase @Inject constructor(
    private val securityRepository: SecurityRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return securityRepository.clearPIN()
    }
}
