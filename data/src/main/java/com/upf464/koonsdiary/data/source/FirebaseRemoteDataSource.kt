package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.MessageData
import kotlinx.coroutines.flow.Flow

interface FirebaseRemoteDataSource {

    val messageFlow: Flow<MessageData>

    fun setMessage(message: MessageData)

    suspend fun getToken(): Result<String>
}
