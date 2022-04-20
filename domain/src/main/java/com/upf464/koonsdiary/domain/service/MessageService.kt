package com.upf464.koonsdiary.domain.service

import com.upf464.koonsdiary.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageService {

    fun getMessageFlow(): Flow<Message>

    fun setMessage(message: Message)

    suspend fun getToken(): Result<String>
}
