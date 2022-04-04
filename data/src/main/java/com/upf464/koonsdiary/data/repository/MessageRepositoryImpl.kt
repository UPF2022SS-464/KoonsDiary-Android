package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.MessageRemoteDataSource
import com.upf464.koonsdiary.domain.repository.MessageRepository
import javax.inject.Inject

internal class MessageRepositoryImpl @Inject constructor(
    private val remote: MessageRemoteDataSource
) : MessageRepository {

    override suspend fun registerFcmToken(token: String): Result<Unit> {
        return remote.registerFcmToken(token).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }
}