package com.upf464.koonsdiary.remote.source

import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
): UserRemoteDataSource {

    override suspend fun loginWithUsername(username: String, password: String): Result<Unit> {
        // TODO("서버 완료 되면 진행")
        return Result.success(Unit)
    }
}