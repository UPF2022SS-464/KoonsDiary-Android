package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.User

interface ShareRepository {

    suspend fun searchUser(keyword: String): Result<List<User>>
}