package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.LockTypeData

interface SecurityLocalDataSource {

    suspend fun setPIN(pin: String): Result<Unit>

    suspend fun getPIN(): Result<String>

    suspend fun getDisposableSalt(): Result<String>

    suspend fun clearPIN(): Result<Unit>

    suspend fun setBiometric(isActive: Boolean): Result<Unit>

    suspend fun fetchLockType(): Result<LockTypeData>
}