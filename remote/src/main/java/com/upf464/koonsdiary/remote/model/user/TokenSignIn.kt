package com.upf464.koonsdiary.remote.model.user

interface TokenSignIn {

    data class Response(
        val userId: Int,
        val userName: String,
        val accessToken: String,
        val refreshToken: String?
    )
}
