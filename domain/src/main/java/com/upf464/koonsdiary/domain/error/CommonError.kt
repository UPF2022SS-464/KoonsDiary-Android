package com.upf464.koonsdiary.domain.error

sealed class CommonError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    object NetworkDisconnected : CommonError()
}
