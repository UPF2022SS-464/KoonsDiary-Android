package com.upf464.koonsdiary.domain.error

sealed class UserError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    data class KakaoLoginCancel(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : UserError(message, cause)

    data class KakaoLoginFailed(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : UserError()
}