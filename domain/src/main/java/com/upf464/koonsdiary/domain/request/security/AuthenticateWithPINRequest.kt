package com.upf464.koonsdiary.domain.request.security

import com.upf464.koonsdiary.domain.request.Request

data class AuthenticateWithPINRequest(
    val pin: String
) : Request