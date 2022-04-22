package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    suspend fun registerFcmToken(token: String): Result<Unit>

    fun getMessageFlow(): Flow<Message>

    suspend fun getToken(): Result<String>
}
