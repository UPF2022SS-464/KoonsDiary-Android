package com.upf464.koonsdiary.kakao.mapper

import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.upf464.koonsdiary.data.error.SignInErrorData

internal fun ClientError.toData() = SignInErrorData.KakaoSignInCancel(message, cause)

internal fun AuthError.toData() = SignInErrorData.KakaoSignInFailed(message, cause)
