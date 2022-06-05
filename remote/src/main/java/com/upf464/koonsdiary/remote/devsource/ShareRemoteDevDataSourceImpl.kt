package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.model.CommentData
import com.upf464.koonsdiary.data.model.DiaryImageData
import com.upf464.koonsdiary.data.model.ShareDiaryData
import com.upf464.koonsdiary.data.model.ShareGroupData
import com.upf464.koonsdiary.data.model.UserData
import com.upf464.koonsdiary.data.source.ShareRemoteDataSource
import java.time.LocalDateTime
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

    override suspend fun fetchGroup(diaryId: Int): Result<ShareGroupData> {
        return Result.success(
            ShareGroupData(
                name = "그룹1",
                imagePath = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg",
                userList = listOf(
                    UserData(
                        username = "Username1",
                        nickname = "nickname1",
                        image = UserData.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                    )
                )
            )
        )
    }

    override suspend fun fetchDiary(diaryId: Int): Result<ShareDiaryData> {
        return Result.success(
            ShareDiaryData(
                user = UserData(
                    username = "Username1",
                    nickname = "nickname1",
                    image = UserData.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                ),
                content = "너굴맨은 사실 라쿤이므로 라쿤맨이라고 불러야 한다구\n" +
                        "좀도둑 처럼 생긴애가 라쿤이고 \n" +
                        "중범죄 처럼 생긴애가 너구리라구 ...",
                imageList = listOf(
                    DiaryImageData(
                        imagePath = "https://cdn.pixabay.com/photo/2019/08/01/12/36/illustration-4377408_960_720.png",
                        comment = ""
                    )
                ),
                commentCount = 2,
                createdDate = LocalDateTime.of(2022, 5, 24, 17, 30)
            )
        )
    }

    override suspend fun fetchDiaryList(groupId: Int): Result<List<ShareDiaryData>> {
        return Result.success(
            listOf(
                ShareDiaryData(
                    user = UserData(
                        username = "Username1",
                        nickname = "nickname1",
                        image = UserData.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                    ),
                    content = "오늘의 할일들\n" +
                            "아무것도 안한다 끝!",
                    commentCount = 3,
                    createdDate = LocalDateTime.of(2022, 6, 1, 17, 30)
                ),
                ShareDiaryData(
                    user = UserData(
                        username = "Username1",
                        nickname = "nickname1",
                        image = UserData.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                    ),
                    content = "너굴맨은 사실 라쿤이므로 라쿤맨이라고 불러야 한다구\n" +
                            "좀도둑 처럼 생긴애가 라쿤이고 \n" +
                            "중범죄 처럼 생긴애가 너구리라구 ...",
                    imageList = listOf(
                        DiaryImageData(
                            imagePath = "https://cdn.pixabay.com/photo/2019/08/01/12/36/illustration-4377408_960_720.png",
                            comment = ""
                        )
                    ),
                    commentCount = 2,
                    createdDate = LocalDateTime.of(2022, 5, 24, 17, 30)
                )
            )
        )
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
