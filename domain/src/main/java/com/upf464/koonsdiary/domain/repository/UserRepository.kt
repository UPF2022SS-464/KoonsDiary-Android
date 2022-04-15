package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.model.User

interface UserRepository {

    suspend fun signUpWithAccount(user: User, password: String): Result<String>

    suspend fun isUsernameDuplicated(username: String): Result<Unit>

    suspend fun isEmailDuplicated(email: String): Result<Unit>

    suspend fun signUpWithKakao(user: User, token: String): Result<Unit>

    suspend fun signInWithAccount(account: String, password: String) : Result<String>

    suspend fun signInWithKakao(token: String): Result<Unit>

    suspend fun signInWithToken(token: String): Result<String?>

    suspend fun generateSaltOf(username: String): Result<String>

    suspend fun fetchSaltOf(username: String): Result<String>

    suspend fun setAutoSignInWithToken(token: String): Result<Unit>

    suspend fun setAutoSignInWithKakao(): Result<Unit>

    suspend fun clearAutoSignIn(): Result<Unit>

    suspend fun getAutoSignIn(): Result<SignInType?>

    suspend fun getAutoSignInToken(): Result<String>

    suspend fun updateUser(nickname: String): Result<Unit>

    suspend fun fetchUserImageList(): Result<List<User.Image>>
}
