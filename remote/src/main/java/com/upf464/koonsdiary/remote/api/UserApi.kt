package com.upf464.koonsdiary.remote.api

import com.upf464.koonsdiary.remote.model.user.EmailSignIn
import com.upf464.koonsdiary.remote.model.user.EmailSignUp
import com.upf464.koonsdiary.remote.model.user.KakaoSignUp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface UserApi {

    @POST("user")
    suspend fun signUpWithEmail(@Body user: EmailSignUp.Request): Response<EmailSignUp.Response>

    @POST("kakaoUser")
    suspend fun signUpWithKakao(@Body user: KakaoSignUp.Request): Response<KakaoSignUp.Response>

    @POST("requestLogin")
    suspend fun signInWithEmail(@Body user: EmailSignIn.Request): Response<EmailSignIn.Response>
}
