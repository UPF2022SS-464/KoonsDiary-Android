package com.upf464.koonsdiary.domain.usecase.message

import com.upf464.koonsdiary.domain.repository.MessageRepository
import javax.inject.Inject

class RegisterFcmTokenUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    suspend operator fun invoke(request: Request): Result<Unit> {
        return messageRepository.registerFcmToken(request.token)
    }

    data class Request(
        val token: String
    )
}
