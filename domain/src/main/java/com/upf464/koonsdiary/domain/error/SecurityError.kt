package com.upf464.koonsdiary.domain.error

sealed class SecurityError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    data class NoStoredPIN(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : SecurityError(message, cause)

    object InvalidPIN : SecurityError()

    object Cancelled : SecurityError()

    object HardwareUnavailable : SecurityError()

    object Timeout : SecurityError()

    object Lockout : SecurityError()

    object NoCredential : SecurityError()

    object Unknown : SecurityError()

    object AuthenticateFailed : SecurityError()
}
