package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.UserData
import com.upf464.koonsdiary.data.source.ShareRemoteDataSource
import javax.inject.Inject

internal class ShareRemoteDevDataSourceImpl @Inject constructor(
) : ShareRemoteDataSource {

    override suspend fun searchUser(keyword: String): Result<List<UserData>> {
        return Result.success(emptyList())
    }
}