package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.data.error.SignInErrorData
import com.upf464.koonsdiary.data.mapper.toData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import com.upf464.koonsdiary.domain.common.errorMap
import com.upf464.koonsdiary.domain.model.SignUpUser
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource
) : UserRepository {

    override suspend fun signInWithUsername(username: String, password: String): Result<Unit> {
        return remote.signInWithUsername(username, password).errorMap { error ->
            when (error) {
                is SignInErrorData -> error.toDomain()
                else -> Exception(error)
            }
        }
    }

    override suspend fun signUpWithUsername(user: SignUpUser): Result<Unit> {
        return remote.signUpWithUsername(user.toData())
    }

    override suspend fun generateSaltOf(username: String): Result<String> {
        return remote.generateSaltOf(username)
    }

    override suspend fun fetchSaltOf(username: String): Result<String> {
        return remote.fetchSaltOf(username)
    }
}