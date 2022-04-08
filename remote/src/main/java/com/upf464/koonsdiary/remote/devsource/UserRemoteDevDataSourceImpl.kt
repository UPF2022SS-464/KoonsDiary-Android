package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.UserData
import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import javax.inject.Inject

internal class UserRemoteDevDataSourceImpl @Inject constructor(
): UserRemoteDataSource {

    override suspend fun signInWithUsername(username: String, password: String): Result<String> {
        return Result.success("")
    }

    override suspend fun signUpWithUsername(user: UserData, password: String): Result<String> {
        return Result.success("")
    }

    override suspend fun signInWithKakao(token: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun signInWithToken(token: String): Result<String?> {
        return Result.success(null)
    }

    override suspend fun signUpWithKakao(user: UserData, token: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun generateSaltOf(username: String): Result<String> {
        return Result.success("")
    }

    override suspend fun fetchSaltOf(username: String): Result<String> {
        return Result.success("")
    }

    override suspend fun updateUser(nickname: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun fetchUserImageList(): Result<List<UserData.Image>> {
        return Result.success(emptyList())
    }
}