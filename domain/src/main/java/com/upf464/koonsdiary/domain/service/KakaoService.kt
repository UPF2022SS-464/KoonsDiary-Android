package com.upf464.koonsdiary.domain.service

interface KakaoService {

    suspend fun signInWithKakao(): Result<Unit>

    suspend fun getAccessToken(): Result<String>
}