package com.upf464.koonsdiary.firebase.service

import com.upf464.koonsdiary.domain.model.Message
import com.upf464.koonsdiary.domain.service.MessageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MessageServiceImpl @Inject constructor(
) : MessageService {

    private val messageFlow = MutableSharedFlow<Message>(extraBufferCapacity = 1)

    override fun getMessageFlow(): Flow<Message> {
        return messageFlow
    }

    override fun setMessage(message: Message) {
        messageFlow.tryEmit(message)
    }
}