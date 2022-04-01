package com.upf464.koonsdiary.domain.repository

interface MessageRepository {

    suspend fun registerFcmToken(token: String): Result<Unit>
}