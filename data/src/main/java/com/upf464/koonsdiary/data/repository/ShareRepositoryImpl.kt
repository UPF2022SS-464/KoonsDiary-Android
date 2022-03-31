package com.upf464.koonsdiary.data.repository

import com.upf464.koonsdiary.data.error.ErrorData
import com.upf464.koonsdiary.data.mapper.toData
import com.upf464.koonsdiary.data.mapper.toDomain
import com.upf464.koonsdiary.data.source.ShareRemoteDataSource
import com.upf464.koonsdiary.domain.common.errorMap
import com.upf464.koonsdiary.domain.model.ShareDiary
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
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun addGroup(group: ShareGroup, inviteUserIdList: List<Int>): Result<Int> {
        return remote.addGroup(group.toData(), inviteUserIdList).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun updateGroup(group: ShareGroup): Result<Int> {
        return remote.updateGroup(group.toData()).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun deleteGroup(groupId: Int): Result<Unit> {
        return remote.deleteGroup(groupId).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun fetchGroupList(): Result<List<ShareGroup>> {
        return remote.fetchGroupList().map { groupList ->
            groupList.map { it.toDomain() }
        }.errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun addDiary(diary: ShareDiary): Result<Int> {
        return remote.addDiary(diary.toData()).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }

    override suspend fun updateDiary(diary: ShareDiary): Result<Int> {
        return remote.updateDiary(diary.toData()).errorMap { error ->
            if (error is ErrorData) error.toDomain()
            else Exception(error)
        }
    }
}