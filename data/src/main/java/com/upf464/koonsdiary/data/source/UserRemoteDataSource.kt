package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.UserData

interface UserRemoteDataSource {

    suspend fun signUpWithUsername(user: UserData, password: String): Result<String>

    suspend fun isUsernameDuplicated(username: String): Result<Unit>

    suspend fun isEmailDuplicated(email: String): Result<Unit>

    suspend fun signUpWithKakao(user: UserData, token: String): Result<Unit>

    suspend fun signInWithUsername(username: String, password: String): Result<String>

    suspend fun signInWithKakao(token: String): Result<Unit>

    suspend fun signInWithToken(token: String): Result<String?>

    suspend fun generateSaltOf(username: String): Result<String>

    suspend fun fetchSaltOf(username: String): Result<String>

    suspend fun updateUser(nickname: String): Result<Unit>

    suspend fun fetchUserImageList(): Result<List<UserData.Image>>
}
