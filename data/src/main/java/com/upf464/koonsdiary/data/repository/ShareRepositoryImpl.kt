package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.data.mapper.toData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.ShareRemoteDataSource
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.domain.repository.ShareRepository
import javax.inject.Inject

internal class ShareRepositoryImpl @Inject constructor(
    private val remote: ShareRemoteDataSource
) : ShareRepository {

    override suspend fun searchUser(keyword: String): Result<List<User>> {
        return remote.searchUser(keyword).map { userList ->
            userList.map { it.toDomain() }
        }
    }

    override suspend fun addGroup(group: ShareGroup, inviteUserIdList: List<Int>): Result<Int> {
        return remote.addGroup(group.toData(), inviteUserIdList)
    }
}