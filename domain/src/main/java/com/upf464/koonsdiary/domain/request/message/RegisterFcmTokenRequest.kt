package com.upf464.koonsdiary.domain.request.message

import com.upf464.koonsdiary.domain.request.Request

data class RegisterFcmTokenRequest(
    val token: String
) : Request