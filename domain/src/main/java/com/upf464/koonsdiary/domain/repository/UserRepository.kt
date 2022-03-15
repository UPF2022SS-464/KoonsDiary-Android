package com.upf464.koonsdiary.domain.repository

interface UserRepository {

    fun loginWithUsername(username: String, password: String) : Result<Unit>
}