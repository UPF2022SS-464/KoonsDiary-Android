package com.upf464.koonsdiary.firebase.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.upf464.koonsdiary.data.model.MessageData
import com.upf464.koonsdiary.data.source.FirebaseRemoteDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject

@AndroidEntryPoint
internal class FirebaseMessagingServiceImpl : FirebaseMessagingService() {

    @Inject lateinit var firebase: FirebaseRemoteDataSource
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate() {
        super.onCreate()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        firebase.setMessage(MessageData.TokenChanged(token))
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
