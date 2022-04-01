package com.upf464.koonsdiary.domain.repository

import com.upf464.koonsdiary.domain.model.Comment
import com.upf464.koonsdiary.domain.model.ShareDiary
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User

interface ShareRepository {

    suspend fun searchUser(keyword: String): Result<List<User>>

    suspend fun addGroup(group: ShareGroup, inviteUserIdList: List<Int>): Result<Int>

    suspend fun updateGroup(group: ShareGroup): Result<Int>

    suspend fun deleteGroup(groupId: Int): Result<Unit>

    suspend fun fetchGroupList(): Result<List<ShareGroup>>

    suspend fun addDiary(diary: ShareDiary): Result<Int>

    suspend fun updateDiary(diary: ShareDiary): Result<Int>

    suspend fun deleteDiary(diaryId: Int): Result<Unit>

    suspend fun fetchDiaryList(groupId: Int): Result<List<ShareDiary>>

    suspend fun addComment(comment: Comment): Result<Unit>

    suspend fun deleteComment(commentId: Int): Result<Unit>

    suspend fun fetchCommentList(diaryId: Int): Result<List<Comment>>

    suspend fun inviteUser(groupId: Int, userIdList: List<Int>): Result<Unit>

    suspend fun kickUser(groupId: Int, userId: Int): Result<Unit>

    suspend fun leaveGroup(groupId: Int): Result<Unit>
}