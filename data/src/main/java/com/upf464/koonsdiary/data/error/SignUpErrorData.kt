package com.upf464.koonsdiary.data.error

sealed class SignUpErrorData(
    message: String? = null,
    cause: Throwable? = null
) : ErrorData(message, cause) {

    object MissingParameters : SignUpErrorData()

    data class UnknownError(
        override val message: String? = null
    ) : SignUpErrorData(message)
}
