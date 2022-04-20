package com.upf464.koonsdiary.domain.error

sealed class SignUpError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    object InvalidUsername : SignUpError()

    object InvalidPassword : SignUpError()

    object InvalidEmail : SignUpError()

    object InvalidNickname : SignUpError()

    object DuplicatedUsername : SignUpError()

    object DuplicatedEmail : SignUpError()

    object DuplicatedKakaoUser : SignUpError()
}
