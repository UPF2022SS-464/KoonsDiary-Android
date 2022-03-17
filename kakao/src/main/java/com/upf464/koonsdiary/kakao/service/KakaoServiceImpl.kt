package com.upf464.koonsdiary.kakao.service

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import com.upf464.koonsdiary.domain.service.KakaoService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

internal class KakaoServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): KakaoService {

    override suspend fun loginWithKakao(): Result<Unit> = suspendCancellableCoroutine { cancellable ->
        with(UserApiClient.instance) {
            if (isKakaoTalkLoginAvailable(context)) {
                loginWithKakaoTalk(context) { token, error ->
                    error?.let {
                        cancellable.resume(Result.failure(error))
                    } ?: cancellable.resume(Result.success(Unit))
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
}