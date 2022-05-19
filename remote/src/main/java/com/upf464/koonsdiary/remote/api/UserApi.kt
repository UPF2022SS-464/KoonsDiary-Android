package com.upf464.koonsdiary.remote.api

import com.upf464.koonsdiary.remote.model.user.EmailSignUp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface UserApi {

    @POST("user")
    suspend fun signUpWithEmail(@Body user: EmailSignUp.Request): Response<EmailSignUp.Response>
}
