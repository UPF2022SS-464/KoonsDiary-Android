package com.upf464.koonsdiary.local.error

sealed class SignInLocalError(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object NoToken : SignInLocalError()
}
