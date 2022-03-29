package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.UserData

interface ShareRemoteDataSource {

    suspend fun searchUser(keyword: String): Result<List<UserData>>
}