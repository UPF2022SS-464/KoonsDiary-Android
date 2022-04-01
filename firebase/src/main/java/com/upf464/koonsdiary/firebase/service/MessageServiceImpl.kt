package com.upf464.koonsdiary.firebase.service

import com.google.firebase.messaging.FirebaseMessaging
import com.upf464.koonsdiary.domain.model.Message
import com.upf464.koonsdiary.domain.service.MessageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

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

    override suspend fun getToken(): Result<String> = suspendCancellableCoroutine { cancellable ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let { token ->
                    cancellable.resume(Result.success(token))
                }
            } else {
                cancellable.resume(Result.failure(Exception("No FCM token")))
            }
        }
    }
}