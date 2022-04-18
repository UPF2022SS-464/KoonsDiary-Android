package com.upf464.koonsdiary.domain.error

sealed class ReportError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    object NoSentiment : ReportError()
}