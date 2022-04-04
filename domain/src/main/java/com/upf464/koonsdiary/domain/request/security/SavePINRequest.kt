package com.upf464.koonsdiary.domain.request.security

import com.upf464.koonsdiary.domain.request.Request

data class SavePINRequest(
    val pin: String
) : Request