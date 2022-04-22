package com.upf464.koonsdiary.data.mapper

import com.upf464.koonsdiary.data.error.SignInErrorData
import com.upf464.koonsdiary.domain.error.SignInError

internal fun SignInErrorData.toDomain() = when (this) {
    SignInErrorData.IncorrectUsernameOrPassword -> SignInError.IncorrectUsernameOrPassword
    SignInErrorData.NoSuchKakaoUser -> SignInError.NoSuchKakaoUser
    SignInErrorData.AccessTokenExpired -> SignInError.AccessTokenExpired
    is SignInErrorData.KakaoSignInCancel -> SignInError.KakaoSignInCancel(message, cause)
    is SignInErrorData.KakaoSignInFailed -> SignInError.KakaoSignInFailed(message, cause)
}
