package com.upf464.koonsdiary.kakao.service

import android.content.Context
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.user.UserApiClient
import com.upf464.koonsdiary.domain.common.errorMap
import com.upf464.koonsdiary.domain.service.KakaoService
import com.upf464.koonsdiary.kakao.mapper.toDomain
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

internal class KakaoServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): KakaoService {

    override suspend fun signInWithKakao(): Result<Unit> {
        val result = suspendCancellableCoroutine<Result<Unit>> { cancellable ->
            with(UserApiClient.instance) {
                if (isKakaoTalkLoginAvailable(context)) {
                    loginWithKakaoTalk(context) { token, error ->
                        error?.let {
                            cancellable.resume(Result.failure(error))
                        } ?: cancellable.resume(Result.success(Unit))

                        // TODO("카톡 앱으로 로그인 실패했을 경우 웹뷰 띄우기")
                    }
                } else {
                    loginWithKakaoAccount(context) { token, error ->
                        error?.let {
                            cancellable.resume(Result.failure(error))
                        } ?: cancellable.resume(Result.success(Unit))
                    }
                }
            }
        }

        return result.errorMap { error ->
            when (error) {
                is ClientError -> error.toDomain()
                is AuthError -> error.toDomain()
                else -> Exception(error)
            }
        }
    }
}