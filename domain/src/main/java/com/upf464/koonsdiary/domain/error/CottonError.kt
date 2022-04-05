package com.upf464.koonsdiary.domain.error

sealed class CottonError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    object EmptyContent : CottonError()

    object InvalidAnswerId : CottonError()
}