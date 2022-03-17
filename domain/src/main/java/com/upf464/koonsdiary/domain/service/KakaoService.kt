package com.upf464.koonsdiary.domain.service

interface KakaoService {

    suspend fun loginWithKakao(): Result<Unit>
}