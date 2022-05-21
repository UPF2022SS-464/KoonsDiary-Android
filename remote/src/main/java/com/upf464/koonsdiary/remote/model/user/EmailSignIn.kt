package com.upf464.koonsdiary.remote.model.user

interface EmailSignIn {

    data class Request(
        val userId: String,
        val password: String
    ) : EmailSignIn

    data class Response(
        val userId: Int,
        val username: String,
        val accessToken: String,
        val refreshToken: String
    )
}
