package com.upf464.koonsdiary.presentation.ui.security

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.ResultReceiver
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.error.BiometricViewError

internal class BiometricActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric)

        intent.getBundleExtra(Constants.KEY_BUNDLE)
            ?.getParcelable<ResultReceiver>(Constants.KEY_RESULT_RECEIVER)?.let { resultReceiver ->
                authenticate(resultReceiver)
            } ?: finish()
    }

    private fun authenticate(resultReceiver: ResultReceiver) {
        var prompt: BiometricPrompt? = null
        var failedCount = 0
        prompt = BiometricPrompt(
            this,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultReceiver.send(Activity.RESULT_OK, null)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    failedCount++

                    if (failedCount >= 2) {
                        prompt?.cancelAuthentication()
                    }
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    resultReceiver.send(
                        Activity.RESULT_OK,
                        Bundle().apply {
                            putParcelable(
                                Constants.KEY_BIOMETRIC_ERROR,
                                BiometricViewError(errorCode, errString.toString())
                            )
                        }
                    )
                    finish()
                }
            }
        )

        val promptInfoBuilder = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_title))
            .setSubtitle(getString(R.string.biometric_subtitle))
            .setNegativeButtonText(getString(R.string.biometric_cancel))

        // 안면 인식
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            promptInfoBuilder.setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        }

        prompt.authenticate(promptInfoBuilder.build())
    }
}
