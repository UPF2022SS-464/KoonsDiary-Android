package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User

interface ShareRepository {

    suspend fun searchUser(keyword: String): Result<List<User>>

    suspend fun addGroup(group: ShareGroup, inviteUserIdList: List<Int>): Result<Int>

    suspend fun updateGroup(group: ShareGroup): Result<Int>

    suspend fun deleteGroup(groupId: Int): Result<Unit>

    suspend fun fetchGroupList(): Result<List<ShareGroup>>
}