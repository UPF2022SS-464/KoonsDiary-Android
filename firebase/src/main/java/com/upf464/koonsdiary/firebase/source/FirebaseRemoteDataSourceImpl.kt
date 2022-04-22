package com.upf464.koonsdiary.firebase.source

import com.google.firebase.messaging.FirebaseMessaging
import com.upf464.koonsdiary.data.model.MessageData
import com.upf464.koonsdiary.data.source.FirebaseRemoteDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

internal class FirebaseRemoteDataSourceImpl @Inject constructor(
    private val messaging: FirebaseMessaging
) : FirebaseRemoteDataSource {

    override val messageFlow = MutableSharedFlow<MessageData>(extraBufferCapacity = 1)

    override fun setMessage(message: MessageData) {
        messageFlow.tryEmit(message)
    }

    override suspend fun getToken(): Result<String> = suspendCancellableCoroutine { cancellable ->
        messaging.token.addOnCompleteListener { task ->
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
