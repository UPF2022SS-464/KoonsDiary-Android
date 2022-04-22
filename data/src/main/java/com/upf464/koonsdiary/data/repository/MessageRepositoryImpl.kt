package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.model.MessageData
import com.upf464.koonsdiary.data.source.FirebaseRemoteDataSource
import com.upf464.koonsdiary.data.source.MessageRemoteDataSource
import com.upf464.koonsdiary.domain.model.Message
import com.upf464.koonsdiary.domain.repository.MessageRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
internal class MessageRepositoryImpl @Inject constructor(
    private val remote: MessageRemoteDataSource,
    private val firebase: FirebaseRemoteDataSource
) : MessageRepository {

    init {
        GlobalScope.launch {
            firebase.messageFlow.collect { message ->
                if (message is MessageData.TokenChanged) {
                    remote.registerFcmToken(message.token)
                }
            }
        }
    }

    override suspend fun registerFcmToken(token: String): Result<Unit> {
        return remote.registerFcmToken(token).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override fun getMessageFlow(): Flow<Message> {
        return firebase.messageFlow.map { it.toDomain() }
    }

    override suspend fun getToken(): Result<String> {
        return firebase.getToken().errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }
}
