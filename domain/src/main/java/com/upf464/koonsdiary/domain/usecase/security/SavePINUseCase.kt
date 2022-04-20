package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.error.SecurityError
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import javax.inject.Inject

class SavePINUseCase @Inject constructor(
    private val securityRepository: SecurityRepository,
    private val hashGenerator: HashGenerator
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        val pin = request.pin
        if (!pin.matches("""^[0-9]{4,8}$""".toRegex())) {
            return Result.failure(SecurityError.InvalidPIN)
        }

        return securityRepository.fetchDisposableSalt().flatMap { salt ->
            val hashedPIN = hashGenerator.hashPasswordWithSalt(pin, salt)
            securityRepository.setPIN(hashedPIN)
        }
    }

    data class Request(
        val pin: String
    )
}
