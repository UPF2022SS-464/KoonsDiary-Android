package com.upf464.koonsdiary.data.source

interface MessageRemoteDataSource {

    suspend fun registerFcmToken(token: String): Result<Unit>
}