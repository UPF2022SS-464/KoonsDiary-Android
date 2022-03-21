package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.SignInType
import com.upf464.koonsdiary.domain.model.SignUpUser

interface UserRepository {

    suspend fun signInWithUsername(username: String, password: String) : Result<Unit>

    suspend fun signUpWithUsername(user: SignUpUser): Result<Unit>

    suspend fun signInWithKakao(token: String): Result<Unit>

    suspend fun signUpWithKakao(token: String, nickname: String): Result<Unit>

    suspend fun generateSaltOf(username: String): Result<String>
    
    suspend fun fetchSaltOf(username: String): Result<String>

    suspend fun setAutoSignInWithToken(token: String): Result<Unit>

    suspend fun setAutoSignInWithKakao(): Result<Unit>

    suspend fun clearAutoSignIn(): Result<Unit>

    suspend fun getAutoSignIn(): Result<SignInType?>

    suspend fun getAutoSignInToken(): Result<String>
}