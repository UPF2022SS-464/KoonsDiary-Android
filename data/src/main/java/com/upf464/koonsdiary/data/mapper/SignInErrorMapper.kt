package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.error.SignInErrorData
import com.upf464.koonsdiary.domain.error.SignInError

internal fun SignInErrorData.toDomain() = when (this) {
    SignInErrorData.IncorrectUsernameOrPassword -> SignInError.IncorrectUsernameOrPassword
    SignInErrorData.NoSuchKakaoUser -> SignInError.NoSuchKakaoUser
}
