package com.upf464.koonsdiary.domain.usecase.message

import com.upf464.koonsdiary.domain.model.Message
import com.upf464.koonsdiary.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessageFlowUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(): Flow<Message> {
        return messageRepository.getMessageFlow()
    }
}
