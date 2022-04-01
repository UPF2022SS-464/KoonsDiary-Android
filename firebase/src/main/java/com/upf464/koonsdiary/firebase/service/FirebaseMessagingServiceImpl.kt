package com.upf464.koonsdiary.firebase.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.upf464.koonsdiary.domain.request.message.RegisterFcmTokenRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.MessageService
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
internal class FirebaseMessagingServiceImpl : FirebaseMessagingService() {

    @Inject lateinit var messageService: MessageService
    @Inject lateinit var registerTokenUseCase: ResultUseCase<RegisterFcmTokenRequest, EmptyResponse>
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var registerJob: Job? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        registerJob?.cancel()
        registerJob = scope.launch {
            registerTokenUseCase(RegisterFcmTokenRequest(token))
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // messageService.setMessage()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}