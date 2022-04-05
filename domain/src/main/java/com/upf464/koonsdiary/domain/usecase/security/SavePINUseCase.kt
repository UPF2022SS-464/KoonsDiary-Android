package com.upf464.koonsdiary.domain.usecase.security

import com.upf464.koonsdiary.common.extension.flatMap
import com.upf464.koonsdiary.domain.common.HashGenerator
import com.upf464.koonsdiary.domain.error.SecurityError
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import com.upf464.koonsdiary.domain.request.security.SavePINRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class SavePINUseCase @Inject constructor(
    private val securityRepository: SecurityRepository,
    private val hashGenerator: HashGenerator
) : ResultUseCase<SavePINRequest, EmptyResponse> {

    override suspend fun invoke(request: SavePINRequest): Result<EmptyResponse> {
        val pin = request.pin
        if (!pin.matches("""^[0-9]{4,8}$""".toRegex())) {
            return Result.failure(SecurityError.InvalidPIN)
        }

        return securityRepository.fetchDisposableSalt().flatMap { salt ->
            val hashedPIN = hashGenerator.hashPasswordWithSalt(pin, salt)
            securityRepository.setPIN(hashedPIN)
        }.map {
            EmptyResponse
        }
    }
}