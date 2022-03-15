package com.upf464.koonsdiary.domain.repository

interface UserRepository {

    suspend fun loginWithUsername(username: String, password: String) : Result<Unit>
}