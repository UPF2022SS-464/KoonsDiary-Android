package com.upf464.koonsdiary.data.error

sealed class ErrorData(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)