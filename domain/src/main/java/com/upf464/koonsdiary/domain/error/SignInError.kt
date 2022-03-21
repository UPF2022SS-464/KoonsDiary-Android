package com.upf464.koonsdiary.domain.error

sealed class SignInError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    data class KakaoSignInCancel(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : SignInError(message, cause)

    data class KakaoSignInFailed(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : SignInError()

    object AccessTokenExpired : SignInError()

    object NoSuchKakaoUser : SignInError()

    object NoAutoSignIn : SignInError()

    object IncorrectUsernameOrPassword : SignInError()
}