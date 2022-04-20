package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.error.SecurityError
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import javax.inject.Inject

class AuthenticateWithPINUseCase @Inject constructor(
    private val securityRepository: SecurityRepository,
    private val hashGenerator: HashGenerator
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        return securityRepository.fetchDisposableSalt().map { salt ->
            hashGenerator.hashPasswordWithSalt(request.pin, salt)
        }.flatMap { hashedPIN ->
            securityRepository.fetchPIN().flatMap { storedPIN ->
                if (hashedPIN == storedPIN) Result.success(Unit)
                else Result.failure(SecurityError.InvalidPIN)
            }
        }
    }

    data class Request(
        val pin: String
    )
}
