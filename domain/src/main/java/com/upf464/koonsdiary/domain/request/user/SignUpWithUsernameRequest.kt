package com.upf464.koonsdiary.domain.request.user

import com.upf464.koonsdiary.domain.request.Request

data class SignUpWithUsernameRequest(
    val email: String,
    val username: String,
    val password: String,
    val nickname: String
) : Request