package com.upf464.koonsdiary.data.error

sealed class SignInErrorData(
    message: String? = null,
    cause: Throwable? = null
) : ErrorData(message, cause) {

    object IncorrectUsernameOrPassword : SignInErrorData()

    object NoSuchKakaoUser : SignInErrorData()

    object AccessTokenExpired : SignInErrorData()

    data class KakaoSignInCancel(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : SignInErrorData(message, cause)

    data class KakaoSignInFailed(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : SignInErrorData(message, cause)
}
