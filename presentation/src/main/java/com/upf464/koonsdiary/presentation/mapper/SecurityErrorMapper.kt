package com.upf464.koonsdiary.presentation.mapper

import androidx.biometric.BiometricPrompt
import com.upf464.koonsdiary.domain.error.SecurityError
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.error.BiometricViewError

internal fun BiometricViewError.toDomain() = when (code) {
    BiometricPrompt.ERROR_HW_UNAVAILABLE -> SecurityError.HardwareUnavailable
    BiometricPrompt.ERROR_TIMEOUT -> SecurityError.Timeout
    Constants.ERROR_AUTHENTICATE_FAILED -> SecurityError.AuthenticateFailed

    BiometricPrompt.ERROR_CANCELED,
    BiometricPrompt.ERROR_USER_CANCELED -> SecurityError.Cancelled

    BiometricPrompt.ERROR_LOCKOUT,
    BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> SecurityError.Lockout

    BiometricPrompt.ERROR_NO_BIOMETRICS,
    BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL -> SecurityError.NoCredential

    else -> SecurityError.Unknown
}