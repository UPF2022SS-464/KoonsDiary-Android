package com.upf464.koonsdiary.domain.usecase.message

import com.upf464.koonsdiary.domain.model.Message
import com.upf464.koonsdiary.domain.service.MessageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessageFlowUseCase @Inject constructor(
    private val messageService: MessageService
) {

    operator fun invoke(): Flow<Message> {
        return messageService.getMessageFlow()
    }
}
