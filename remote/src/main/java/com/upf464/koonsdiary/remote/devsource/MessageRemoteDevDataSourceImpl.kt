package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.source.MessageRemoteDataSource
import javax.inject.Inject

internal class MessageRemoteDevDataSourceImpl @Inject constructor(
) : MessageRemoteDataSource {

    override suspend fun registerFcmToken(token: String): Result<Unit> {
        return Result.success(Unit)
    }
}