package com.upf464.koonsdiary.domain.error

sealed class UserError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    data class KakaoSignInCancel(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : UserError(message, cause)

    data class KakaoSignInFailed(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : UserError()
}