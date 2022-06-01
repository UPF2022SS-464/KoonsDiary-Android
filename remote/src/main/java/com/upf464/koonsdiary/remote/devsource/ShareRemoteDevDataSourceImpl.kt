package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.CommentData
import com.upf464.koonsdiary.data.model.ShareDiaryData
import com.upf464.koonsdiary.data.model.ShareGroupData
import com.upf464.koonsdiary.data.model.UserData
import com.upf464.koonsdiary.data.source.ShareRemoteDataSource
import javax.inject.Inject

internal class ShareRemoteDevDataSourceImpl @Inject constructor() : ShareRemoteDataSource {

    override suspend fun searchUser(keyword: String): Result<List<UserData>> {
        return Result.success(
            listOf(
                UserData(
                    id = 1,
                    username = "Username1",
                    nickname = "Nickname1",
                    image = UserData.Image(
                        path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"
                    )
                ),
                UserData(
                    id = 2,
                    username = "Username2",
                    nickname = "Nickname2",
                    image = UserData.Image(
                        path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"
                    )
                )
            )
        )
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
        return Result.success(
            listOf(
                ShareGroupData(
                    id = 1,
                    name = "Name1",
                    manager = UserData(
                        id = 1,
                        username = "Username1",
                        nickname = "Nickname1"
                    ),
                    imagePath = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg",
                    userList = listOf(
                        UserData(
                            id = 1,
                            username = "Username1",
                            nickname = "Nickname1",
                            image = UserData.Image(
                                path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"
                            )
                        ),
                        UserData(
                            id = 2,
                            username = "Username2",
                            nickname = "Nickname2",
                            image = UserData.Image(
                                path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"
                            )
                        )
                    )
                )
            )
        )
    }

    override suspend fun addDiary(diary: ShareDiaryData): Result<Int> {
        return Result.success(1)
    }

    override suspend fun updateDiary(diary: ShareDiaryData): Result<Int> {
        return Result.success(1)
    }

    override suspend fun deleteDiary(diaryId: Int): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun fetchDiaryList(groupId: Int): Result<List<ShareDiaryData>> {
        return Result.success(emptyList())
    }

    override suspend fun addComment(comment: CommentData): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun deleteComment(commentId: Int): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun fetchCommentList(diaryId: Int): Result<List<CommentData>> {
        return Result.success(emptyList())
    }

    override suspend fun inviteUser(groupId: Int, userIdList: List<Int>): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun kickUser(groupId: Int, userId: Int): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun leaveGroup(groupId: Int): Result<Unit> {
        return Result.success(Unit)
    }
}
