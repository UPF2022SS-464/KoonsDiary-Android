package com.upf464.koonsdiary.data.model

import java.time.LocalDateTime

data class CommentData(
    val id: Int = 0,
    val diaryId: Int,
    val user: UserData = UserData(),
    val content: String = "",
    val createdDate: LocalDateTime = LocalDateTime.now()
)