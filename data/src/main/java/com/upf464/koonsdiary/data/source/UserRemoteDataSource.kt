package com.upf464.koonsdiary.data.source

interface UserRemoteDataSource {

    fun loginWithUsername(username: String, password: String): Result<Unit>
}