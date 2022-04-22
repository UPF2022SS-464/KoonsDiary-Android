package com.upf464.koonsdiary.kakao.source

import android.content.Context
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.upf464.koonsdiary.common.extension.errorMap
import com.upf464.koonsdiary.data.error.SignInErrorData
import com.upf464.koonsdiary.data.source.KakaoRemoteDataSource
import com.upf464.koonsdiary.kakao.mapper.toData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

internal class KakaoRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userApiClient: UserApiClient,
    private val authApiClient: AuthApiClient
) : KakaoRemoteDataSource {

    override suspend fun signInKakaoAccount(): Result<Unit> {
        val result = suspendCancellableCoroutine<Result<Unit>> { cancellable ->
            with(userApiClient) {
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
                is ClientError -> error.toData()
                is AuthError -> error.toData()
                else -> Exception(error)
            }
        }
    }

    override suspend fun getKakaoAccessToken(): Result<String> {
        val token = suspendCancellableCoroutine<String?> { cancellable ->
            val token = authApiClient.tokenManagerProvider.manager.getToken()?.accessToken
            cancellable.resume(token)
        }

        return token?.let { Result.success(token) }
            ?: Result.failure(SignInErrorData.AccessTokenExpired)
    }
}
