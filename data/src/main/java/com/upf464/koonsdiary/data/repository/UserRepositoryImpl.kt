package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.data.error.SignInErrorData
import com.upf464.koonsdiary.data.mapper.toData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.UserLocalDataSource
import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import com.upf464.koonsdiary.domain.common.errorMap
import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.model.SignUpUser
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val local: UserLocalDataSource
) : UserRepository {

    override suspend fun signInWithUsername(username: String, password: String): Result<String> {
        return remote.signInWithUsername(username, password).errorMap { error ->
            when (error) {
                is SignInErrorData -> error.toDomain()
                else -> Exception(error)
            }
        }
    }

    override suspend fun signUpWithUsername(user: SignUpUser): Result<String> {
        return remote.signUpWithUsername(user.toData())
    }

    override suspend fun signInWithKakao(token: String): Result<Unit> {
        return remote.signInWithKakao(token)
    }

    override suspend fun signUpWithKakao(token: String, nickname: String): Result<Unit> {
        return remote.signUpWithKakao(token, nickname)
    }

    override suspend fun generateSaltOf(username: String): Result<String> {
        return remote.generateSaltOf(username)
    }

    override suspend fun fetchSaltOf(username: String): Result<String> {
        return remote.fetchSaltOf(username)
    }

    override suspend fun setAutoSignInWithToken(token: String): Result<Unit> {
        return local.setAutoSignIn(SignInType.USERNAME.name, token)
    }

    override suspend fun setAutoSignInWithKakao(): Result<Unit> {
        return local.setAutoSignIn(SignInType.KAKAO.name)
    }

    override suspend fun clearAutoSignIn(): Result<Unit> {
        return local.clearAutoSignIn()
    }

    override suspend fun getAutoSignIn(): Result<SignInType?> {
        return local.getAutoSignInType().flatMap { type ->
            Result.success(type?.let { SignInType.valueOf(type) })
        }
    }

    override suspend fun getAutoSignInToken(): Result<String> {
        return local.getAutoSignInToken().errorMap { SignInError.AccessTokenExpired }
    }
}