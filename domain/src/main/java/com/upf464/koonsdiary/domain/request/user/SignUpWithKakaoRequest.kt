package com.upf464.koonsdiary.domain.request.user

import com.upf464.koonsdiary.domain.request.Request

data class SignUpWithKakaoRequest(
    val username: String,
    val nickname: String
) : Request