package com.upf464.koonsdiary.domain.request.user

import com.upf464.koonsdiary.domain.request.Request

data class SignInWithUsernameRequest(
    val username: String,
    val password: String
) : Request
