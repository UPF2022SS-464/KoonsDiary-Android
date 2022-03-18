package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.request.SignInWithKakaoRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.KakaoService
import javax.inject.Inject

internal class SignInWithKakaoUseCase @Inject constructor(
    private val kakaoService: KakaoService
) : ResultUseCase<SignInWithKakaoRequest, EmptyResponse> {

    override suspend fun invoke(request: SignInWithKakaoRequest): Result<EmptyResponse> {
        return kakaoService.signInWithKakao().flatMap {
            Result.success(EmptyResponse)
        }
    }
}