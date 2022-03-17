package com.upf464.koonsdiary.kakao.mapper

import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.upf464.koonsdiary.domain.error.UserError

internal fun ClientError.toDomain() = UserError.KakaoLoginCancel(message, cause)

internal fun AuthError.toDomain() = UserError.KakaoLoginFailed(message, cause)