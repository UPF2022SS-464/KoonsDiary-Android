package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource
) : UserRepository {

    override suspend fun loginWithUsername(username: String, password: String): Result<Unit> {
        return remote.loginWithUsername(username, password)
    }
}