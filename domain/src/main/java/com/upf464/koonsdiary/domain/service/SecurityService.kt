package com.upf464.koonsdiary.domain.service

interface SecurityService {

    suspend fun authenticateWithBiometric(): Result<Unit>
}
