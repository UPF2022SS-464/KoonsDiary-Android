package com.upf464.koonsdiary.domain.usecase.message

import com.upf464.koonsdiary.domain.model.Message
import com.upf464.koonsdiary.domain.request.message.GetMessageFlowRequest
import com.upf464.koonsdiary.domain.service.MessageService
import com.upf464.koonsdiary.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetMessageFlowUseCase @Inject constructor(
    private val messageService: MessageService
) : FlowUseCase<GetMessageFlowRequest, Message> {

    override fun invoke(request: GetMessageFlowRequest): Flow<Message> {
        return messageService.getMessageFlow()
    }
}