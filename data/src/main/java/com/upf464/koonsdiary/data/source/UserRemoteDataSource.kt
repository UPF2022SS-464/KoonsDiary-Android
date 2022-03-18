package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.SignUpUserData

interface UserRemoteDataSource {

    suspend fun signInWithUsername(username: String, password: String): Result<Unit>

    suspend fun signUpWithUsername(user: SignUpUserData): Result<Unit>

    suspend fun generateSaltOf(username: String): Result<String>
    
    suspend fun fetchSaltOf(username: String): Result<String>
}
