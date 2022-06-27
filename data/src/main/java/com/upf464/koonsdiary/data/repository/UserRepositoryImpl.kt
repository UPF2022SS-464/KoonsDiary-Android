package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.mapper.toData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.KakaoRemoteDataSource
import com.upf464.koonsdiary.data.source.UserLocalDataSource
import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val local: UserLocalDataSource,
    private val kakao: KakaoRemoteDataSource
) : UserRepository {

    override suspend fun isUsernameDuplicated(username: String): Result<Unit> {
        return remote.isUsernameDuplicated(username).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun isEmailDuplicated(email: String): Result<Unit> {
        return remote.isEmailDuplicated(email).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun signInWithAccount(account: String, password: String): Result<String> {
        return remote.signInWithAccount(account, password).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun signUpWithAccount(user: User, password: String): Result<String> {
        return remote.signUpWithAccount(user.toData(), password).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun signInWithKakao(token: String): Result<Unit> {
        return remote.signInWithKakao(token).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun signInKakaoAccount(): Result<Unit> {
        return kakao.signInKakaoAccount().errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun getKakaoAccessToken(): Result<String> {
        return kakao.getKakaoAccessToken().errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun signInWithToken(token: String): Result<String?> {
        return remote.signInWithToken(token).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun signUpWithKakao(user: User, token: String): Result<Unit> {
        return remote.signUpWithKakao(user.toData(), token).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun generateSaltOf(username: String): Result<String> {
        return remote.generateSaltOf(username).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun fetchSaltOf(username: String): Result<String> {
        return remote.fetchSaltOf(username).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun setAutoSignInWithToken(token: String): Result<Unit> {
        return local.setAutoSignIn(SignInType.USERNAME.name, token).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun setAutoSignInWithKakao(): Result<Unit> {
        return local.setAutoSignIn(SignInType.KAKAO.name).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun clearAutoSignIn(): Result<Unit> {
        return local.clearAutoSignIn().errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun getAutoSignIn(): Result<SignInType?> {
        return local.getAutoSignInType().map { type ->
            type?.let { SignInType.valueOf(type) }
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun getAutoSignInToken(): Result<String> {
        return local.getAutoSignInToken().errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun updateUser(nickname: String?, imageId: Int?): Result<Unit> {
        return remote.updateUser(nickname, imageId).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun fetchUserImageList(): Result<List<User.Image>> {
        return remote.fetchUserImageList().map { imageList ->
            imageList.map { it.toDomain() }
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }
}
