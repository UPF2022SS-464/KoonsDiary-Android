package com.upf464.koonsdiary.domain.error

sealed class CacheError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    data class Diary(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : CacheError()
}
