package com.upf464.koonsdiary.remote.source

import com.upf464.koonsdiary.data.error.SignInErrorData
import com.upf464.koonsdiary.data.error.SignUpErrorData
import com.upf464.koonsdiary.data.model.UserData
import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import com.upf464.koonsdiary.remote.api.UserApi
import com.upf464.koonsdiary.remote.model.user.EmailSignIn
import com.upf464.koonsdiary.remote.model.user.EmailSignUp
import com.upf464.koonsdiary.remote.model.user.KakaoSignUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
    private val okHttpClient: OkHttpClient
) : UserRemoteDataSource {

    override suspend fun signInWithAccount(
        account: String,
        password: String
    ): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val result = userApi.signInWithEmail(
                EmailSignIn.Request(
                    userId = account,
                    password = password
                )
            )

            val response = result.body() ?: throw errorFromResult(result)
            addAuthorizationInterceptor(response.accessToken)
            response.refreshToken
        }
    }

    override suspend fun signUpWithAccount(
        user: UserData,
        password: String
    ): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val result = userApi.signUpWithEmail(
                EmailSignUp.Request(
                    userId = user.username,
                    password = password,
                    email = user.email ?: throw SignUpErrorData.MissingParameters,
                    nickname = user.nickname,
                    imageId = user.imageId
                )
            )

            val response = result.body() ?: throw errorFromResult(result)
            addAuthorizationInterceptor(response.accessToken)
            response.refreshToken
        }
    }

    override suspend fun isUsernameDuplicated(username: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun isEmailDuplicated(email: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun signInWithKakao(token: String): Result<Unit> {
        return Result.failure(SignInErrorData.NoSuchKakaoUser)
    }

    override suspend fun signInWithToken(token: String): Result<String?> {
        return Result.success(null)
    }

    override suspend fun signUpWithKakao(
        user: UserData,
        token: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val result = userApi.signUpWithKakao(
                KakaoSignUp.Request(
                    token = token,
                    userId = user.username,
                    nickname = user.nickname,
                    imageId = user.imageId
                )
            )

            val response = result.body() ?: throw errorFromResult(result)
            addAuthorizationInterceptor(response.accessToken)
        }
    }

    override suspend fun generateSaltOf(username: String): Result<String> {
        return Result.success("")
    }

    override suspend fun fetchSaltOf(username: String): Result<String> {
        return Result.success("")
    }

    override suspend fun updateUser(nickname: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun fetchUserImageList(): Result<List<UserData.Image>> {
        return Result.success(
            listOf(
                UserData.Image(id = 1, path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"),
                UserData.Image(id = 2, path = "https://cdn.pixabay.com/photo/2020/09/09/02/12/smearing-5556288_960_720.jpg"),
                UserData.Image(id = 3, path = "https://blog.kakaocdn.net/dn/XlVZH/btqIH50as13/LwCnDkeRzRz9kETtUMaHyk/img.jpg"),
                UserData.Image(
                    id = 4,
                    path = "https://r1.community.samsung.com/t5/image/serverpage/image-id/1078560iA1D80891B2B80672/image-size/large?v=v2&px=999"
                ),
                UserData.Image(id = 5, path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"),
                UserData.Image(id = 6, path = "https://cdn.pixabay.com/photo/2020/09/09/02/12/smearing-5556288_960_720.jpg"),
                UserData.Image(id = 7, path = "https://blog.kakaocdn.net/dn/XlVZH/btqIH50as13/LwCnDkeRzRz9kETtUMaHyk/img.jpg"),
                UserData.Image(
                    id = 8,
                    path = "https://r1.community.samsung.com/t5/image/serverpage/image-id/1078560iA1D80891B2B80672/image-size/large?v=v2&px=999"
                ),
                UserData.Image(id = 9, path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"),
                UserData.Image(id = 10, path = "https://cdn.pixabay.com/photo/2020/09/09/02/12/smearing-5556288_960_720.jpg"),
                UserData.Image(id = 11, path = "https://blog.kakaocdn.net/dn/XlVZH/btqIH50as13/LwCnDkeRzRz9kETtUMaHyk/img.jpg"),
                UserData.Image(
                    id = 12,
                    path = "https://r1.community.samsung.com/t5/image/serverpage/image-id/1078560iA1D80891B2B80672/image-size/large?v=v2&px=999"
                )
            )
        )
    }

    private fun addAuthorizationInterceptor(accessToken: String) {
        okHttpClient.interceptors().add(
            Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", accessToken)
                    .build()
                return@Interceptor chain.proceed(request)
            }
        )
    }

    private fun <T> errorFromResult(result: Response<T>) =
        SignUpErrorData.UnknownError("${result.code()} - ${result.message()}")
}
