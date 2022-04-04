package com.upf464.koonsdiary.domain.repository

interface SecurityRepository {

    suspend fun setPIN(pin: String): Result<Unit>

    suspend fun getPIN(): Result<String>

    suspend fun getDisposableSalt(): Result<String>

    suspend fun clearPIN(): Result<Unit>
}