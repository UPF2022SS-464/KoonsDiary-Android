package com.upf464.koonsdiary.domain.request

data class SignUpWithUsernameRequest(
    val email: String,
    val username: String,
    val password: String,
    val nickname: String
) : Request