package com.upf464.koonsdiary.data.source

interface KakaoRemoteDataSource {

    suspend fun signInKakaoAccount(): Result<Unit>

    suspend fun getKakaoAccessToken(): Result<String>
}
