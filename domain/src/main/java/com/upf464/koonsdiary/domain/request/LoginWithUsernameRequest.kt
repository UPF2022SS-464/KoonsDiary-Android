package com.upf464.koonsdiary.domain.request

data class LoginWithUsernameRequest(
    val username: String,
    val password: String
) : Request
