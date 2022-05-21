package com.upf464.koonsdiary.remote.model.user

interface KakaoSignUp {

    data class Request(
        val token: String,
        val userId: String,
        val nickname: String,
        val imageId: Int
    )

    data class Response(
        val accessToken: String,
        val userId: String
    )
}
