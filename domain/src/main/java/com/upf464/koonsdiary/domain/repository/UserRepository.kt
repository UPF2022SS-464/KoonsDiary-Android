package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.SignUpUser

interface UserRepository {

    suspend fun signInWithUsername(username: String, password: String) : Result<Unit>

    suspend fun signUpWithAccount(user: SignUpUser): Result<Unit>

    suspend fun generateSaltOf(username: String): Result<String>
    
    suspend fun fetchSaltOf(username: String): Result<String>
}