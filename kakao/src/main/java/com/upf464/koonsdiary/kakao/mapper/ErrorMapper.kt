package com.upf464.koonsdiary.kakao.mapper

import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.upf464.koonsdiary.domain.error.SignInError

internal fun ClientError.toDomain() = SignInError.KakaoSignInCancel(message, cause)

internal fun AuthError.toDomain() = SignInError.KakaoSignInFailed(message, cause)
