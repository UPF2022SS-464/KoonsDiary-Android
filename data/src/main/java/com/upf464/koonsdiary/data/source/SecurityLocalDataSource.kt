package com.upf464.koonsdiary.data.source

interface SecurityLocalDataSource {

    suspend fun setPIN(pin: String): Result<Unit>

    suspend fun getPIN(): Result<String>

    suspend fun getDisposableSalt(): Result<String>

    suspend fun clearPIN(): Result<Unit>
}