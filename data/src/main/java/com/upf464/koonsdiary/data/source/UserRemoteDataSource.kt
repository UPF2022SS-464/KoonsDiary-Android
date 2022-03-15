package com.upf464.koonsdiary.data.source

interface UserRemoteDataSource {

    suspend fun loginWithUsername(username: String, password: String): Result<Unit>
}