package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.SignUpUserData

interface UserRemoteDataSource {

    suspend fun signInWithUsername(username: String, password: String): Result<String>

    suspend fun signUpWithUsername(user: SignUpUserData): Result<String>

    suspend fun signInWithKakao(token: String): Result<Unit>

    suspend fun signUpWithKakao(token: String, nickname: String): Result<Unit>

    suspend fun generateSaltOf(username: String): Result<String>
    
    suspend fun fetchSaltOf(username: String): Result<String>
}
