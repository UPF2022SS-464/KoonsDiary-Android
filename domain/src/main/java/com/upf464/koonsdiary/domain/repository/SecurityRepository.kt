package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.error.LockType

interface SecurityRepository {

    suspend fun setPIN(pin: String): Result<Unit>

    suspend fun fetchPIN(): Result<String>

    suspend fun fetchDisposableSalt(): Result<String>

    suspend fun clearPIN(): Result<Unit>

    suspend fun setBiometric(isActive: Boolean): Result<Unit>

    suspend fun fetchLockType(): Result<LockType>
}
