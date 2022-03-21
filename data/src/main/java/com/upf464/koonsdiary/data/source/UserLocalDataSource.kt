package com.upf464.koonsdiary.data.source

interface UserLocalDataSource {

    suspend fun setAutoSignIn(type: String, token: String? = null): Result<Unit>

    suspend fun getAutoSignInType(): Result<String?>

    suspend fun getAutoSignInToken(): Result<String>

    suspend fun clearAutoSignIn(): Result<Unit>
}
