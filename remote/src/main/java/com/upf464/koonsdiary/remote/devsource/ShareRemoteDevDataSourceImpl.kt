package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.ShareDiaryData
import com.upf464.koonsdiary.data.model.ShareGroupData
import com.upf464.koonsdiary.data.model.UserData
import com.upf464.koonsdiary.data.source.ShareRemoteDataSource
import javax.inject.Inject

internal class ShareRemoteDevDataSourceImpl @Inject constructor(
) : ShareRemoteDataSource {

    override suspend fun searchUser(keyword: String): Result<List<UserData>> {
        return Result.success(emptyList())
    }

    override suspend fun addGroup(group: ShareGroupData, inviteUserIdList: List<Int>): Result<Int> {
        return Result.success(1)
    }

    override suspend fun updateGroup(group: ShareGroupData): Result<Int> {
        return Result.success(1)
    }

    override suspend fun deleteGroup(groupId: Int): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun fetchGroupList(): Result<List<ShareGroupData>> {
        return Result.success(emptyList())
    }

    override suspend fun addDiary(diary: ShareDiaryData): Result<Int> {
        return Result.success(1)
    }
}