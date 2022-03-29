package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.data.error.SignInErrorData
import com.upf464.koonsdiary.data.mapper.toData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.UserLocalDataSource
import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import com.upf464.koonsdiary.domain.common.errorMap
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.model.User
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

    override suspend fun signUpWithUsername(user: User, password: String): Result<String> {
        return remote.signUpWithUsername(user.toData(), password)
    }

    override suspend fun signInWithKakao(token: String): Result<Unit> {
        return remote.signInWithKakao(token)
    }

    override suspend fun signInWithToken(token: String): Result<String?> {
        return remote.signInWithToken(token)
    }

    override suspend fun signUpWithKakao(user: User, token: String): Result<Unit> {
        return remote.signUpWithKakao(user.toData(), token)
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
        return local.getAutoSignInType().map { type ->
            type?.let { SignInType.valueOf(type) }
        }
    }

    override suspend fun getAutoSignInToken(): Result<String> {
        return local.getAutoSignInToken().errorMap { SignInError.AccessTokenExpired }
    }
}