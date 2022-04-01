package com.upf464.koonsdiary.domain.usecase.message

import com.upf464.koonsdiary.domain.repository.MessageRepository
import com.upf464.koonsdiary.domain.request.message.RegisterFcmTokenRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import javax.inject.Inject

internal class RegisterFcmTokenUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) : ResultUseCase<RegisterFcmTokenRequest, EmptyResponse> {

    override suspend fun invoke(request: RegisterFcmTokenRequest): Result<EmptyResponse> {
        return messageRepository.registerFcmToken(request.token).map {
            EmptyResponse
        }
    }
}