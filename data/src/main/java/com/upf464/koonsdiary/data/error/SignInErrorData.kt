package com.upf464.koonsdiary.data.error

sealed class SignInErrorData(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    object IncorrectUsernameOrPassword : SignInErrorData()
}