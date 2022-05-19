package com.upf464.koonsdiary.remote.model.user

interface EmailSignUp {

    data class Request(
        val userId: String,
        val password: String,
        val email: String,
        val nickname: String,
        val imageId: Int
    ) : EmailSignUp

    data class Response(
        val accessToken: String,
        val refreshToken: String,
        val userId: String
    )
}
