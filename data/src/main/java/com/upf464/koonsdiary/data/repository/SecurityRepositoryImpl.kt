package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.SecurityLocalDataSource
import com.upf464.koonsdiary.domain.repository.SecurityRepository
import javax.inject.Inject

internal class SecurityRepositoryImpl @Inject constructor(
    private val local: SecurityLocalDataSource
) : SecurityRepository {

    override suspend fun setPIN(pin: String): Result<Unit> {
        return local.setPIN(pin).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun getPIN(): Result<String> {
        return local.getPIN().errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun getDisposableSalt(): Result<String> {
        return local.getDisposableSalt().errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun clearPIN(): Result<Unit> {
        return local.clearPIN().errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }
}