package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.ShareGroupData
import com.upf464.koonsdiary.data.model.UserData

interface ShareRemoteDataSource {

    suspend fun searchUser(keyword: String): Result<List<UserData>>

    suspend fun addGroup(group: ShareGroupData, inviteUserIdList: List<Int>): Result<Int>

    suspend fun updateGroup(group: ShareGroupData): Result<Int>

    suspend fun deleteGroup(groupId: Int): Result<Unit>

    suspend fun fetchGroupList(): Result<List<ShareGroupData>>
}