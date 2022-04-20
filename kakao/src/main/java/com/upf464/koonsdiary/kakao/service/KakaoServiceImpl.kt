package com.upf464.koonsdiary.kakao.service

import android.content.Context
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.domain.error.SignInError
import com.upf464.koonsdiary.domain.service.KakaoService
import com.upf464.koonsdiary.kakao.mapper.toDomain
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

internal class KakaoServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : KakaoService {

    override suspend fun signInWithKakao(): Result<Unit> {
        val result = suspendCancellableCoroutine<Result<Unit>> { cancellable ->
            with(UserApiClient.instance) {
                if (isKakaoTalkLoginAvailable(context)) {
                    loginWithKakaoTalk(context) { _, error ->
                        error?.let {
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                cancellable.resume(Result.failure(error))
                            }

                            loginWithKakaoAccount(context) { _, error ->
                                error?.let {
                                    cancellable.resume(Result.failure(error))
                                } ?: cancellable.resume(Result.success(Unit))
                            }
                        } ?: cancellable.resume(Result.success(Unit))
                    }
                } else {
                    loginWithKakaoAccount(context) { _, error ->
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

    override suspend fun getAccessToken(): Result<String> {
        val token = suspendCancellableCoroutine<String?> { cancellable ->
            val token = AuthApiClient.instance.tokenManagerProvider.manager.getToken()?.accessToken
            cancellable.resume(token)
        }

        return token?.let { Result.success(token) }
            ?: Result.failure(SignInError.AccessTokenExpired)
    }
}
