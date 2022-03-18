package com.upf464.koonsdiary.domain.request

data class SignInWithUsernameRequest(
    val username: String,
    val password: String
) : Request
