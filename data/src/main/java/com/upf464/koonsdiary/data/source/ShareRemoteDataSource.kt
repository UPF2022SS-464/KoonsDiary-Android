package com.upf464.koonsdiary.data.source

import com.upf464.koonsdiary.data.model.CommentData
import com.upf464.koonsdiary.data.model.ShareDiaryData
import com.upf464.koonsdiary.data.model.ShareGroupData
import com.upf464.koonsdiary.data.model.UserData

interface ShareRemoteDataSource {

    suspend fun searchUser(keyword: String): Result<List<UserData>>

    suspend fun addGroup(group: ShareGroupData, inviteUserIdList: List<Int>): Result<Int>

    suspend fun updateGroup(group: ShareGroupData): Result<Int>

    suspend fun deleteGroup(groupId: Int): Result<Unit>

    suspend fun fetchGroupList(): Result<List<ShareGroupData>>

    suspend fun addDiary(diary: ShareDiaryData): Result<Int>

    suspend fun updateDiary(diary: ShareDiaryData): Result<Int>

    suspend fun deleteDiary(diaryId: Int): Result<Unit>

    suspend fun fetchDiaryList(groupId: Int): Result<List<ShareDiaryData>>

    suspend fun addComment(comment: CommentData): Result<Unit>

    suspend fun deleteComment(commentId: Int): Result<Unit>

    suspend fun fetchCommentList(diaryId: Int): Result<List<CommentData>>

    suspend fun inviteUser(groupId: Int, userIdList: List<Int>): Result<Unit>

    suspend fun kickUser(groupId: Int, userId: Int): Result<Unit>
}