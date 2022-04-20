package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.error.SignInErrorData
import com.upf464.koonsdiary.data.model.UserData
import com.upf464.koonsdiary.data.source.UserRemoteDataSource
import javax.inject.Inject

internal class UserRemoteDevDataSourceImpl @Inject constructor() : UserRemoteDataSource {

    override suspend fun signInWithUsername(username: String, password: String): Result<String> {
        return Result.success("")
    }

    override suspend fun signUpWithUsername(user: UserData, password: String): Result<String> {
        return Result.success("")
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

    override suspend fun signUpWithKakao(user: UserData, token: String): Result<Unit> {
        return Result.success(Unit)
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
                UserData.Image(
                    id = 1,
                    path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"
                ),
                UserData.Image(
                    id = 2,
                    path = "https://cdn.pixabay.com/photo/2020/09/09/02/12/smearing-5556288_960_720.jpg"
                ),
                UserData.Image(
                    id = 3,
                    path = "https://blog.kakaocdn.net/dn/XlVZH/btqIH50as13/LwCnDkeRzRz9kETtUMaHyk/img.jpg"
                ),
                UserData.Image(
                    id = 4,
                    path = "https://r1.community.samsung.com/t5/image/serverpage/image-id/1078560iA1D80891B2B80672/image-size/large?v=v2&px=999"
                )
            )
        )
    }
}
