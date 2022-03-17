package com.upf464.koonsdiary.domain.usecase

import com.upf464.koonsdiary.domain.common.flatMap
import com.upf464.koonsdiary.domain.request.EmptyRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.service.KakaoService
import javax.inject.Inject

internal class LoginWithKakaoUseCase @Inject constructor(
    private val kakaoService: KakaoService
) : ResultUseCase<EmptyRequest, EmptyResponse> {

    override suspend fun invoke(request: EmptyRequest): Result<EmptyResponse> {
        return kakaoService.loginWithKakao().flatMap {
            Result.success(EmptyResponse)
        }
    }
}