package com.upf464.koonsdiary.domain.model

import java.time.LocalDateTime

data class ShareDiary(
    val id: Int = 0,
    val group: ShareGroup = ShareGroup(),
    val user: User = User(),
    val content: String,
    val imageList: List<DiaryImage>,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val lastModifiedDate: LocalDateTime = LocalDateTime.now()
)
