package com.upf464.koonsdiary.domain.model

import java.time.LocalDateTime

data class Comment(
    val id: Int = 0,
    val diaryId: Int,
    val user: User = User(),
    val content: String = "",
    val createdDate: LocalDateTime = LocalDateTime.now()
)