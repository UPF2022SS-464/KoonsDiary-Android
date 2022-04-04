package com.upf464.koonsdiary.data.error

sealed class SecurityErrorData(
    message: String? = null,
    cause: Throwable? = null
) : ErrorData(message, cause) {

    object NoStoredPIN : SecurityErrorData()
}