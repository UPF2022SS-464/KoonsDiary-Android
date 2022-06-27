package com.upf464.koonsdiary.presentation.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import com.upf464.koonsdiary.domain.service.SecurityService
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.error.BiometricViewError
import com.upf464.koonsdiary.presentation.mapper.toDomain
import com.upf464.koonsdiary.presentation.ui.security.BiometricActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

internal class SecurityServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SecurityService {

    override suspend fun authenticateWithBiometric(): Result<Unit> =
        suspendCancellableCoroutine { continuation ->
            val intent = Intent(context, BiometricActivity::class.java)
                .putExtra(
                    Constants.KEY_BUNDLE,
                    Bundle().apply {
                        putParcelable(Constants.KEY_RESULT_RECEIVER, resultReceiver(continuation))
                    }
                )
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            context.startActivity(intent)
        }

    private fun resultReceiver(
        continuation: CancellableContinuation<Result<Unit>>
    ): ResultReceiver = object : ResultReceiver(Handler(Looper.getMainLooper())) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == Activity.RESULT_OK) {
                resultData?.getParcelable<BiometricViewError>(Constants.KEY_BIOMETRIC_ERROR)
                    ?.let { error ->
                        continuation.resume(Result.failure(error.toDomain()))
                    }
                    ?: continuation.resume(Result.success(Unit))
            }
        }
    }
}
